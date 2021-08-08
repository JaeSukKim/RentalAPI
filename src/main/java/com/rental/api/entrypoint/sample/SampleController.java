package com.rental.api.entrypoint.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/sample")
public class SampleController {

    @PostMapping("/health-check")
    public HealthCheckResponse healthCheck(HttpServletRequest request) {

        return HealthCheckResponse.builder()
                .clientIp(request.getRemoteAddr())
                .build();
    }

    @GetMapping("/client-info")
    public ClientInfoResponse getClientInfo(HttpServletRequest request) {

        return ClientInfoResponse.builder().device(
                ClientInfoResponse.DeviceInfo.builder()
                        .os("window")
                        .name("LG gram")
                        .build())
                .clientIp(request.getRemoteAddr())
                .build();
    }

}
