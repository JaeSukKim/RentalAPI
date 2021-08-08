package com.rental.api.core.usecase.cmd;

import com.rental.api.core.domain.CmdInfoDomain;

import java.util.List;

public interface ICmdInfoDataprovider {
    List<CmdInfoDomain> getCmdsByCId(long cId);

    CmdInfoDomain getLatestCmdByCId(long cId);

    void updateCmdReceivedTime(long cId, String cmdTime);
}
