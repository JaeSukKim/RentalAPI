package com.rental.api.core.usecase.healthcheck;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rental.api.core.domain.CmdInfoDomain;
import com.rental.api.core.domain.DeviceInfoDomain;
import com.rental.api.core.domain.DeviceIpHistoryDomain;
import com.rental.api.core.domain.DeviceMacHistoryDomain;
import com.rental.api.core.exception.BusinessException;
import com.rental.api.core.usecase.IUsecase;
import com.rental.api.core.usecase.IUsecase.*;
import com.rental.api.core.usecase.cmd.ICmdInfoDataprovider;
import com.rental.api.core.usecase.device.IDeviceInfoDataprovider;
import com.rental.api.core.usecase.healthcheck.HealthCheckUsecase.Command;
import com.rental.api.core.usecase.healthcheck.HealthCheckUsecase.Result;
import com.rental.api.core.util.CommonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class HealthCheckUsecase implements IUsecase<Command, Result> {

    private final IDeviceInfoDataprovider deviceInfoDataprovider;
    private final ICmdInfoDataprovider cmdInfoDataprovider;

    @Override
    public Result execute(Command input) {

        String mac1 = CommonUtil.validAncConvertMacAddress(input.ethernetAdapterMac);
        String mac2 = CommonUtil.validAncConvertMacAddress(input.wirelessLanAdapterMac);

        if (!StringUtils.hasLength(mac1) && !StringUtils.hasLength(mac2)) {
            throw new BusinessException("Either mac1 or mac2 is required.");
        }

        String internalIp = input.getEthernetAdapterIp();
        if ("0.0.0.0".equals(internalIp)) {
            internalIp = input.getWirelessLanAdapterIp();
        }

        DeviceInfoDomain deviceInfoDomain = deviceInfoDataprovider.getDeviceInfoByMac(mac1, mac2);
        // first call
        if (Objects.isNull(deviceInfoDomain)) {
            // step1. create device_info data
            deviceInfoDomain = DeviceInfoDomain.builder()
                    .mac1(mac1)
                    .mac2(mac2)
                    .internalIp(internalIp)
                    .externalIp(input.getClientIp())
                    .build();

            DeviceInfoDomain savedDeviceInfoDomain = deviceInfoDataprovider.saveDeviceInfo(deviceInfoDomain);
            if (Objects.isNull(savedDeviceInfoDomain)) {
                throw new BusinessException("Failed to create device info");
            }

            Result result = new Result();
            result.setDevice(savedDeviceInfoDomain);

            return result;
        }

        // Already exist
        // step1. latest_conn_time update
        DeviceInfoDomain updateDeviceInfo = DeviceInfoDomain.builder()
                .deviceId(deviceInfoDomain.getDeviceId())
                .mac1(mac1)
                .mac2(mac2)
                .contractId(deviceInfoDomain.getContractId())
                .internalIp(internalIp)
                .externalIp(input.getClientIp())
                .firstConnTime(deviceInfoDomain.getFirstConnTime())
                .build();
        updateDeviceInfo = deviceInfoDataprovider.saveDeviceInfo(updateDeviceInfo);
        if (Objects.isNull(updateDeviceInfo)) {
            throw new BusinessException("Failed to update device info");
        }

        Result result = new Result();
        result.setDevice(updateDeviceInfo);

        // step2. check IP and update IP history
        if (!Objects.equals(updateDeviceInfo.getExternalIp(), deviceInfoDomain.getExternalIp())) {

            DeviceIpHistoryDomain deviceIpHistoryDomain = DeviceIpHistoryDomain.builder()
                    .deviceId(deviceInfoDomain.getDeviceId())
                    .externalIp(deviceInfoDomain.getExternalIp())
                    .internalIp(deviceInfoDomain.getInternalIp())
                    .build();
            deviceIpHistoryDomain = deviceInfoDataprovider.saveDeviceIpHistory(deviceIpHistoryDomain);
            if (Objects.isNull(deviceIpHistoryDomain)) {
                log.error("Failed to save changed IP data : from[{}, {}]",
                        deviceInfoDomain.getExternalIp(), deviceInfoDomain.getInternalIp());
            }
        }

        // step3. check MAC anc update MAC history
        if (!Objects.equals(updateDeviceInfo.getMac1(), deviceInfoDomain.getMac1())
                || !Objects.equals(updateDeviceInfo.getMac2(), deviceInfoDomain.getMac2())) {

            DeviceMacHistoryDomain deviceMacHistoryDomain = DeviceMacHistoryDomain.builder()
                    .deviceId(deviceInfoDomain.getDeviceId())
                    .oldMac1(deviceInfoDomain.getMac1())
                    .oldMac2(deviceInfoDomain.getMac2())
                    .newMac1(updateDeviceInfo.getMac1())
                    .newMac2(updateDeviceInfo.getMac2())
                    .build();
            deviceMacHistoryDomain = deviceInfoDataprovider.saveDeviceMacHistory(deviceMacHistoryDomain);
            if (Objects.isNull(deviceMacHistoryDomain)) {
                log.error("Failed to save changed MAC data : from[{}, {}] - to[{}, {}]",
                        deviceInfoDomain.getMac1(), deviceInfoDomain.getMac2(),
                        updateDeviceInfo.getMac1(), updateDeviceInfo.getMac2());
            }
        }

        // step4. check 'cid' and get cmd data
        if (Objects.isNull(updateDeviceInfo.getContractId()) || 0L == updateDeviceInfo.getContractId()) {
            return result;
        }

        List<CmdInfoDomain> cmdInfoDomains = cmdInfoDataprovider.getCmdsByCId(updateDeviceInfo.getContractId());
        if (CollectionUtils.isEmpty(cmdInfoDomains)) {
            return result;
        }

        Duration duration = Duration.between(deviceInfoDomain.getLatestConnTime(), updateDeviceInfo.getLatestConnTime());
        // 2분이내 : 최신 커맨드
        if (duration.getSeconds() < 180) {
            if (cmdInfoDomains.size() > 1) {
                CmdInfoDomain lastCmdInfoDomain = cmdInfoDomains.get(cmdInfoDomains.size()-1);
                log.info("Multiple commands exist within 3 minutes. Total command size : {}, Last command info - {}"
                        , cmdInfoDomains.size(), lastCmdInfoDomain);
                result.setCommand(lastCmdInfoDomain);
                cmdInfoDataprovider.updateCmdReceivedTime(lastCmdInfoDomain.getContractId(), lastCmdInfoDomain.getCommandTime());
                return result;
            }
            log.info("Only one command exist within 3 minutes. Last command info - {}", cmdInfoDomains.get(0));
            result.setCommand(cmdInfoDomains.get(0));
            cmdInfoDataprovider.updateCmdReceivedTime(cmdInfoDomains.get(0).getContractId(), cmdInfoDomains.get(0).getCommandTime());
            return result;
        }

        // 2분이후 : 최신 MSG 커맨드
        for (CmdInfoDomain cmdInfoDomain : cmdInfoDomains) {
            if ("msg".equals(cmdInfoDomain.getCommand())) {
                log.info("Message command exist after 3 minutes. Total command size : {}, Last msg command Info - {}"
                        , cmdInfoDomains.size(), cmdInfoDomain);
                result.setCommand(cmdInfoDomain);
                cmdInfoDataprovider.updateCmdReceivedTime(cmdInfoDomain.getContractId(), cmdInfoDomain.getCommandTime());
                return result;
            }
        }

        log.info("Message command does not exist after 3 minutes. Total command size : {}", cmdInfoDomains.size());
        return result;
    }

    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Command implements Serializable, ICommand {

        private static final long serialVersionUID = -1281224108023864799L;

        private String clientIp;

        @JsonProperty("ethernet-adapter-mac")
        private String ethernetAdapterMac;    //이더넷 어댑터 물리적 주소

        @JsonProperty("ethernet-adapter-ip")
        private String ethernetAdapterIp;     //이더넷 어댑터 IPv4 주소

        @JsonProperty("wireless-lan-adapter-mac")
        private String wirelessLanAdapterMac;    //무선 LAN 어댑터 물리적 주소

        @JsonProperty("wireless-lan-adapter-ip")
        private String wirelessLanAdapterIp;     //무선 LAN 어댑터 IPv4 주소
    }

    @NoArgsConstructor
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result implements Serializable, IResult {

        private static final long serialVersionUID = -1909384792865966194L;

        @JsonProperty("device")
        private Object device;

        @JsonProperty("command")
        private Object command;
    }

}
