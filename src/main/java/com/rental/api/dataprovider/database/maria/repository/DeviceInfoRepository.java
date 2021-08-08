package com.rental.api.dataprovider.database.maria.repository;

import com.rental.api.dataprovider.database.maria.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {

    DeviceInfo findByMac1(String mac1);
    DeviceInfo findByMac2(String mac2);
}

