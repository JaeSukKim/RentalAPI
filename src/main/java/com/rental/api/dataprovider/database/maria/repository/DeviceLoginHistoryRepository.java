package com.rental.api.dataprovider.database.maria.repository;

import com.rental.api.dataprovider.database.maria.entity.DeviceLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceLoginHistoryRepository extends JpaRepository<DeviceLoginHistory, Long> {
}
