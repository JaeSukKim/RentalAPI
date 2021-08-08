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
public class DeviceLoginHistoryDomain {

    private Long loginId;
    private Long contractId;
    private LocalDateTime loginTime;

}
