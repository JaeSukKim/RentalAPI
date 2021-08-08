package com.rental.api.dataprovider;

import com.rental.api.core.domain.DeviceInfoDomain;
import com.rental.api.core.domain.DeviceIpHistoryDomain;
import com.rental.api.core.domain.DeviceLoginHistoryDomain;
import com.rental.api.core.domain.DeviceMacHistoryDomain;
import com.rental.api.core.usecase.device.IDeviceInfoDataprovider;
import com.rental.api.dataprovider.database.maria.entity.DeviceInfo;
import com.rental.api.dataprovider.database.maria.entity.DeviceIpHistory;
import com.rental.api.dataprovider.database.maria.entity.DeviceLoginHistory;
import com.rental.api.dataprovider.database.maria.entity.DeviceMacHistory;
import com.rental.api.dataprovider.database.maria.repository.DeviceInfoRepository;
import com.rental.api.dataprovider.database.maria.repository.DeviceIpHistoryRepository;
import com.rental.api.dataprovider.database.maria.repository.DeviceLoginHistoryRepository;
import com.rental.api.dataprovider.database.maria.repository.DeviceMacHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class DeviceInfoDataprovider implements IDeviceInfoDataprovider {

    private final DeviceInfoRepository deviceInfoRepository;
    private final DeviceIpHistoryRepository deviceIpHistoryRepository;
    private final DeviceMacHistoryRepository deviceMacHistoryRepository;
    private final DeviceLoginHistoryRepository deviceLoginHistoryRepository;

    @Override
    public DeviceInfoDomain getDeviceInfoByEMac(String mac) {
        DeviceInfo deviceInfo = deviceInfoRepository.findByMac1(mac);
        if (Objects.isNull(deviceInfo)) {
            return null;
        }
        return DeviceInfoMapper.mapper.toDomain(deviceInfo);
    }

    @Override
    public DeviceInfoDomain getDeviceInfoByWMac(String mac) {
        DeviceInfo deviceInfo = deviceInfoRepository.findByMac2(mac);
        if (Objects.isNull(deviceInfo)) {
            return null;
        }
        return DeviceInfoMapper.mapper.toDomain(deviceInfo);
    }

    @Override
    public DeviceInfoDomain getDeviceInfoByMac(String eMac, String wMac) {
        DeviceInfo deviceInfo;
        if (StringUtils.hasLength(wMac)) {
            deviceInfo = deviceInfoRepository.findByMac2(wMac);
            if (Objects.isNull(deviceInfo) && StringUtils.hasLength(eMac)) {
                deviceInfo = deviceInfoRepository.findByMac1(eMac);
            }
        } else {
            deviceInfo = deviceInfoRepository.findByMac1(eMac);
        }
        if (Objects.isNull(deviceInfo)) {
            return null;
        }

        return DeviceInfoMapper.mapper.toDomain(deviceInfo);
    }

    @Override
    public DeviceInfoDomain saveDeviceInfo(DeviceInfoDomain deviceInfoDomain) {
        DeviceInfo deviceInfo = DeviceInfoMapper.mapper.fromDomain(deviceInfoDomain);
        deviceInfo = deviceInfoRepository.save(deviceInfo);
        if (Objects.isNull(deviceInfo)) {
            return null;
        }
        return DeviceInfoMapper.mapper.toDomain(deviceInfo);
    }

    @Override
    public DeviceIpHistoryDomain saveDeviceIpHistory(DeviceIpHistoryDomain deviceIpHistoryDomain) {
        DeviceIpHistory deviceIpHistory = DeviceInfoMapper.mapper.fromDomain(deviceIpHistoryDomain);
        deviceIpHistory = deviceIpHistoryRepository.save(deviceIpHistory);
        if (Objects.isNull(deviceIpHistory)) {
            return null;
        }
        return DeviceInfoMapper.mapper.toDomain(deviceIpHistory);
    }

    @Override
    public DeviceMacHistoryDomain saveDeviceMacHistory(DeviceMacHistoryDomain deviceMacHistoryDomain) {

        DeviceMacHistory deviceMacHistory = DeviceInfoMapper.mapper.fromDomain(deviceMacHistoryDomain);
        deviceMacHistory = deviceMacHistoryRepository.save(deviceMacHistory);
        if (Objects.isNull(deviceMacHistory)) {
            return null;
        }
        return DeviceInfoMapper.mapper.toDomain(deviceMacHistory);
    }

    @Override
    public DeviceLoginHistoryDomain saveDeviceLoginHistory(DeviceLoginHistoryDomain deviceLoginHistoryDomain) {
        DeviceLoginHistory deviceLoginHistory = DeviceInfoMapper.mapper.fromDomain(deviceLoginHistoryDomain);
        deviceLoginHistory = deviceLoginHistoryRepository.save(deviceLoginHistory);
        if (Objects.isNull(deviceLoginHistory)) {
            return null;
        }

        return DeviceInfoMapper.mapper.toDomain(deviceLoginHistory);
    }

    @Mapper(unmappedTargetPolicy= ReportingPolicy.IGNORE )
    public interface DeviceInfoMapper {
        DeviceInfoMapper mapper = Mappers.getMapper(DeviceInfoMapper.class);

        DeviceInfoDomain toDomain(DeviceInfo deviceInfo);

        DeviceInfo fromDomain(DeviceInfoDomain deviceInfoDomain);

        @Mappings({
                @Mapping(target = "deviceInfo.deviceId", source = "deviceId"),
        })
        DeviceIpHistory fromDomain(DeviceIpHistoryDomain deviceIpHistoryDomain);

        @Mappings({
                @Mapping(target = "deviceId", source = "deviceInfo.deviceId"),
        })
        DeviceIpHistoryDomain toDomain(DeviceIpHistory deviceIpHistory);

        @Mappings({
                @Mapping(target = "deviceInfo.deviceId", source = "deviceId"),
        })
        DeviceMacHistory fromDomain(DeviceMacHistoryDomain deviceMacHistoryDomain);

        @Mappings({
                @Mapping(target = "deviceId", source = "deviceInfo.deviceId"),
        })
        DeviceMacHistoryDomain toDomain(DeviceMacHistory deviceMacHistory);

        DeviceLoginHistory fromDomain(DeviceLoginHistoryDomain deviceLoginHistoryDomain);

        DeviceLoginHistoryDomain toDomain(DeviceLoginHistory deviceLoginHistory);
    }

}
