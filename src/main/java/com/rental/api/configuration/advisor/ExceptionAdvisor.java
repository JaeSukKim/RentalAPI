package com.rental.api.configuration.advisor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rental.api.core.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.rental.api.entrypoint")
public class ExceptionAdvisor {

    @Order(Ordered.LOWEST_PRECEDENCE-100)
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse businessException(BusinessException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .name(BusinessException.class.getName())
                .message(exception.getMessage())
                .build();

        log.error("{}", errorResponse);
        return errorResponse;
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(Exception exception, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .name(Exception.class.getName())
                .message(exception.getMessage())
                .build();

        log.error("{}", errorResponse);
        return errorResponse;
    }

    @AllArgsConstructor
    @ToString
    @Builder
    @JsonInclude(NON_NULL)
    public static class ErrorResponse {
        @JsonProperty("name")
        String name;

        @JsonProperty("message")
        String message;
    }
}
