package com.rental.api.dataprovider.database.maria.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(
        name = "t_device_login_history",
        indexes = {
                @Index(name = "ix_device_login_history_1", columnList = "cid", unique = false)
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="login_id")
    private Long login_id;

    @Column(name="cid")
    private Long contractId;

    @CreatedDate
    @Column(name="login_time")
    private LocalDateTime loginTime;

}
