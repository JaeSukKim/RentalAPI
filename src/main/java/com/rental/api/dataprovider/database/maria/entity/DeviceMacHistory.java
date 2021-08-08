package com.rental.api.dataprovider.database.maria.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "t_device_mac_history"
)
@EntityListeners(AuditingEntityListener.class)
public class DeviceMacHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dev_mac_history_id")
    private Long devMacHistoryId;

    @ManyToOne
    @JoinColumn(name="device_id")
    private DeviceInfo deviceInfo;

    @Column(name="old_mac1")
    private String oldMac1;

    @Column(name="old_mac2")
    private String oldMac2;

    @Column(name="new_mac1")
    private String newMac1;

    @Column(name="new_mac2")
    private String newMac2;

    @CreatedDate
    @Column(name="changed_time")
    private LocalDateTime changedTime;

}
