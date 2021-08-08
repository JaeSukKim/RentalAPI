package com.rental.api.entrypoint.login;

import com.rental.api.core.usecase.login.LoginUsecase;
import com.rental.api.entrypoint.RestApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
public class LoginController {

    private final LoginUsecase loginUsecase;

    @PostMapping("/login")
    public LoginUsecase.Result login(@RequestBody @Validated LoginUsecase.Command command,
                                                     HttpServletRequest request) {

        String uri = "/login";
        log.info("URI : {}, Request : {}" , uri, command);

        LoginUsecase.Result result = loginUsecase.execute(command);
        return RestApiResponse.ok(uri, command, result, true);

    }
}
