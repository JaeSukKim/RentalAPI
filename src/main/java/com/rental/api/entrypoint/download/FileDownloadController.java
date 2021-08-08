package com.rental.api.entrypoint.download;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RequiredArgsConstructor
@Slf4j
@RestController
public class FileDownloadController {

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("filePath") String encFilePath,
                                             HttpServletRequest request) throws IOException {

        String filePath = new String(Base64.getDecoder().decode(encFilePath));
        Path path = Paths.get(filePath);
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        String fileName = filePath.substring(filePath.lastIndexOf("\\")+ 1);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {}

        // Fallback to the default content type if type could not be determined
        if(!StringUtils.hasLength(contentType)) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
