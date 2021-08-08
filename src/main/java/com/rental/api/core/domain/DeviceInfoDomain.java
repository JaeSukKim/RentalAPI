package com.rental.api.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfoDomain {

    private Long deviceId;
    private Long contractId;
    private String mac1;
    private String mac2;
    private String internalIp;
    private String externalIp;
    private LocalDateTime firstConnTime;
    private LocalDateTime latestConnTime;

}
