package com.rental.api.entrypoint.sample;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class ClientInfoResponse {

    @JsonProperty("client-ip")
    private String clientIp;

    @JsonProperty("device-name")
    private DeviceInfo device;

    @Data
    @Builder
    @JsonInclude(NON_NULL)
    public static class DeviceInfo {

        private String os;

        private String name;
    }
}
