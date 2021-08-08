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
public class DeviceMacHistoryDomain {

    private Long deviceMacHistoryId;
    private Long deviceId;
    private String oldMac1;
    private String oldMac2;
    private String newMac1;
    private String newMac2;
    private LocalDateTime changedTime;

}
