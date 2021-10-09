package com.rental.api.dataprovider.database.maria.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(
        name = "t_device_info",
        indexes = {
                @Index(name = "ix_device_info_1", columnList = "device_id", unique = true)
        }
)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="device_id")
    private Long deviceId;

    @Column(name="cid")
    private Long contractId;

    @Column(name="mac1")
    private String mac1;

    @Column(name="mac2")
    private String mac2;

    @Column(name="internal_ip")
    private String internalIp;

    @Column(name="external_ip", nullable = false)
    private String externalIp;

    @CreatedDate
    @Column(name="first_conn_time")
    private LocalDateTime firstConnTime;

    @LastModifiedDate
    @Column(name="latest_conn_time")
    private LocalDateTime latestConnTime;
}
