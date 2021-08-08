package com.rental.api.dataprovider.database.maria.repository;

import com.rental.api.dataprovider.database.maria.entity.DeviceMacHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceMacHistoryRepository extends JpaRepository<DeviceMacHistory, Long> {
}
