package com.rental.api.dataprovider.database.maria.repository;

import com.rental.api.dataprovider.database.maria.entity.DeviceInfo;
import com.rental.api.dataprovider.database.maria.entity.DeviceIpHistory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class DeviceInfoRepositoryTest {

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DeviceIpHistoryRepository deviceIpHistoryRepository;

    private final String MAC1 = "B4:B6:76:03:04:80";
    private final String MAC2 = "18:67:B0:3C:F2:91";
    private final String INTERNAL_IP = "192.168.0.100";
    private final String EXTERNAL_IP = "212.230.24.21";

    @Test
    @Order(0)
    @DisplayName("t_device_info 데이터 저장")
    void test01() {

        DeviceInfo deviceInfo = DeviceInfo.builder()
                .mac1(MAC1)
                .mac2(MAC2)
                .internalIp(INTERNAL_IP)
                .externalIp(EXTERNAL_IP)
                .build();

        DeviceInfo savedDeviceInfo = deviceInfoRepository.save(deviceInfo);
        Assertions.assertTrue(Objects.nonNull(savedDeviceInfo));
        Assertions.assertTrue(Objects.nonNull(savedDeviceInfo.getDeviceId()));

        System.out.println(savedDeviceInfo.toString());
    }

    @Test
    @Order(1)
    @DisplayName("t_device_ip_history 데이터 저장")
    void test02() {

        DeviceInfo deviceInfo = deviceInfoRepository.findByMac1(MAC1);
        if (Objects.isNull(deviceInfo)) {
            return;
        }

        DeviceIpHistory deviceIpHistory = DeviceIpHistory.builder()
                .deviceInfo(DeviceInfo.builder()
                        .deviceId(deviceInfo.getDeviceId())
                        .build())
                .externalIp(deviceInfo.getExternalIp())
                .internalIp(deviceInfo.getInternalIp())
                .build();

        DeviceIpHistory savedDeviceIpHistory = deviceIpHistoryRepository.save(deviceIpHistory);
        Assertions.assertTrue(Objects.nonNull(savedDeviceIpHistory));
        Assertions.assertTrue(Objects.nonNull(savedDeviceIpHistory.getDevIpHistoryId()));

        System.out.println(savedDeviceIpHistory.toString());

    }
}
