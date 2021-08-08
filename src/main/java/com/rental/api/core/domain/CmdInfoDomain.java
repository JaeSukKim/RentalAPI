package com.rental.api.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmdInfoDomain {

    private Long contractId;

    private String command;

    private String value;

    private String commandTime;

    private String receiveTime;

    private String executeTime;

}
