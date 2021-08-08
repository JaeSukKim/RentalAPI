package com.rental.api.entrypoint.sample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class HealthCheckResponse {

    @JsonProperty("client-ip")
    private String clientIp;

    @JsonProperty("request-time")
    private String requestTime;

    public static HealthCheckResponseBuilder builder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return new HealthCheckResponseBuilder().requestTime(sdf.format(System.currentTimeMillis()));
    }
}
