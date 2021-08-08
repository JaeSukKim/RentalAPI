package com.rental.api.dataprovider.database.maria.entity;

import com.rental.api.dataprovider.database.maria.entity.index.CmdInfoId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(
        name = "t_cmd"
)
@IdClass(CmdInfoId.class)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class CmdInfo {

    @Id
    @Column(name="cid")
    private Long cId;

    @Column(name="cmd")
    private String cmd;

    @Column(name="value")
    private String value;

    @Id
    @Column(name="cmdtime")
    private String cmdTime;

    @Column(name="rcvtime")
    private String rcvTime;

    @Column(name="exectime")
    private String execTime;
}
