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
public class ClientVersionInfoDomain {

    private Long versionId;
    private String clientVersion;
    private String clientFilePath;
    private String updater;
    private LocalDateTime updateTime;

}
