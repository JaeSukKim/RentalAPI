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
        name = "t_client_version_info",
        indexes = {
                @Index(name = "ix_client_version_info_1", columnList = "update_time", unique = true)
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class ClientVersionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="version_id")
    private Long versionId;

    @Column(name="client_version", nullable = false)
    private String clientVersion;

    @Column(name="client_file_path", nullable = false)
    private String clientFilePath;

    @Column(name="updater", nullable = false)
    private String updater;

    @CreatedDate
    @Column(name="update_time", unique = true)
    private LocalDateTime updateTime;

}
