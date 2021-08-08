package com.rental.api.entrypoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@ToString
@Slf4j
public class RestApiResponse implements Serializable {
    private static final long serialVersionUID = -5045473404321428069L;

    @JsonIgnore
    public static <T> T ok(String uri, Object requestBody, @NonNull T responseBody, boolean logging) {
        if (logging) {
            log.info("URI : {}, Response : {}" , uri, responseBody);
        }
        return responseBody;
    }
}
