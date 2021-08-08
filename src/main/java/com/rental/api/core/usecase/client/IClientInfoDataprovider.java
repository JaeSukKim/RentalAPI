package com.rental.api.core.usecase.client;

import com.rental.api.core.domain.ClientVersionInfoDomain;

public interface IClientInfoDataprovider {
    ClientVersionInfoDomain getLatestClientVersionInfo();
}
