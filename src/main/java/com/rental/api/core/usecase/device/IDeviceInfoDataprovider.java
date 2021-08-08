package com.rental.api.core.usecase.device;

import com.rental.api.core.domain.DeviceInfoDomain;
import com.rental.api.core.domain.DeviceIpHistoryDomain;
import com.rental.api.core.domain.DeviceLoginHistoryDomain;
import com.rental.api.core.domain.DeviceMacHistoryDomain;

public interface IDeviceInfoDataprovider {

    DeviceInfoDomain getDeviceInfoByEMac(String mac);
    DeviceInfoDomain getDeviceInfoByWMac(String mac);
    DeviceInfoDomain getDeviceInfoByMac(String eMac, String wMac);

    DeviceInfoDomain saveDeviceInfo(DeviceInfoDomain deviceInfoDomain);
    DeviceIpHistoryDomain saveDeviceIpHistory(DeviceIpHistoryDomain deviceIpHistoryDomain);
    DeviceMacHistoryDomain saveDeviceMacHistory(DeviceMacHistoryDomain deviceMacHistoryDomain);
    DeviceLoginHistoryDomain saveDeviceLoginHistory(DeviceLoginHistoryDomain deviceLoginHistoryDomain);
}
