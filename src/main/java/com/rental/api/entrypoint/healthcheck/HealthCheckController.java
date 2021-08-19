package com.rental.api.entrypoint.healthcheck;

import com.rental.api.core.usecase.healthcheck.HealthCheckUsecase;
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
public class HealthCheckController {

    private final HealthCheckUsecase healthCheckUsecase;

    @PostMapping("/health-check")
    public HealthCheckUsecase.Result healthCheck(@RequestBody @Validated HealthCheckUsecase.Command command,
                                                 HttpServletRequest request) {

        command.setClientIp(request.getRemoteAddr());
        log.info("URI : {}, Request : {}" , request.getRequestURI(), command);

        HealthCheckUsecase.Result result = healthCheckUsecase.execute(command);
        return RestApiResponse.ok(request.getRequestURI(), command, result, false);
    }
}
