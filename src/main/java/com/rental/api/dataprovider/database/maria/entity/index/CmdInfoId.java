package com.rental.api.dataprovider.database.maria.entity.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdInfoId implements Serializable {

    private static final long serialVersionUID = 5004098883043111981L;

    private Long cId;

    private String cmdTime;

}
