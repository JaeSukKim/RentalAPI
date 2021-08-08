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
public class DeviceIpHistoryDomain {

    private Long deviceIpHistoryId;
    private Long deviceId;
    private String internalIp;
    private String externalIp;
    private LocalDateTime changedTime;

}
