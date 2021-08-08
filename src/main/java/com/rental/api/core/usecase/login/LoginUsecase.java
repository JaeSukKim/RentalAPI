package com.rental.api.core.usecase.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rental.api.core.domain.ClientVersionInfoDomain;
import com.rental.api.core.domain.DeviceInfoDomain;
import com.rental.api.core.domain.DeviceLoginHistoryDomain;
import com.rental.api.core.exception.BusinessException;
import com.rental.api.core.usecase.IUsecase;
import com.rental.api.core.usecase.IUsecase.*;
import com.rental.api.core.usecase.client.IClientInfoDataprovider;
import com.rental.api.core.usecase.device.IDeviceInfoDataprovider;
import com.rental.api.core.util.CommonUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

import com.rental.api.core.usecase.login.LoginUsecase.*;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginUsecase implements IUsecase<Command, Result> {

    private final IDeviceInfoDataprovider deviceInfoDataprovider;
    private final IClientInfoDataprovider clientInfoDataprovider;

    @Override
    public Result execute(Command input) {

        String mac1 = CommonUtil.validAncConvertMacAddress(input.ethernetAdapterMac);
        String mac2 = CommonUtil.validAncConvertMacAddress(input.wirelessLanAdapterMac);

        if (!StringUtils.hasLength(mac1) && !StringUtils.hasLength(mac2)) {
            throw new BusinessException("Either mac1 or mac2 is required.");
        }

        // Write login history
        DeviceInfoDomain deviceInfoDomain = deviceInfoDataprovider.getDeviceInfoByMac(mac1, mac2);
        if (Objects.nonNull(deviceInfoDomain)) {
            if (Objects.nonNull(deviceInfoDomain.getContractId()) && 0L != deviceInfoDomain.getContractId()) {
                DeviceLoginHistoryDomain loginHistoryDomain = DeviceLoginHistoryDomain.builder()
                        .contractId(deviceInfoDomain.getContractId())
                        .build();
                loginHistoryDomain = deviceInfoDataprovider.saveDeviceLoginHistory(loginHistoryDomain);
                if (Objects.isNull(loginHistoryDomain)) {
                    log.error("Failed to save login history[{}]", deviceInfoDomain.getContractId());
                }
            }
        }

        // Get client version
        ClientVersionInfoDomain clientVersionInfoDomain = clientInfoDataprovider.getLatestClientVersionInfo();
        if (Objects.isNull(clientVersionInfoDomain)) {
            throw new BusinessException("Client version info does not exist");
        }

        Result result = new Result();
        result.setClientVersion(clientVersionInfoDomain.getClientVersion());
        result.setClientFilePath(clientVersionInfoDomain.getClientFilePath());
        return result;
    }

    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Command implements Serializable, ICommand {
        private static final long serialVersionUID = -1343216268930513120L;

        @JsonProperty("ethernet-adapter-mac")
        private String ethernetAdapterMac;    //이더넷 어댑터 물리적 주소

        @JsonProperty("wireless-lan-adapter-mac")
        private String wirelessLanAdapterMac;    //무선 LAN 어댑터 물리적 주소

    }

    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable, IResult {
        private static final long serialVersionUID = -4950145872467077084L;

        private String clientVersion;
        private String clientFilePath;

    }
}
