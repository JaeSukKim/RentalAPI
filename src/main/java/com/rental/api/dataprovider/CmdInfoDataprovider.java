package com.rental.api.dataprovider;

import com.rental.api.core.domain.CmdInfoDomain;
import com.rental.api.core.usecase.cmd.ICmdInfoDataprovider;
import com.rental.api.dataprovider.database.maria.entity.CmdInfo;
import com.rental.api.dataprovider.database.maria.repository.CmdInfoCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class CmdInfoDataprovider implements ICmdInfoDataprovider {

    private final CmdInfoCustomRepository cmdInfoCustomRepository;

    @Override
    public List<CmdInfoDomain> getCmdsByCId(long cId) {
        List<CmdInfo> cmdInfos = cmdInfoCustomRepository.findByCId(cId);
        if (CollectionUtils.isEmpty(cmdInfos)) {
            return null;
        }
        return CmdInfoMapper.mapper.toDomains(cmdInfos);
    }

    @Override
    public CmdInfoDomain getLatestCmdByCId(long cId) {
        CmdInfo cmdInfo = cmdInfoCustomRepository.findOneByCId(cId);
        if (Objects.isNull(cmdInfo)){
            return null;
        }
        return CmdInfoMapper.mapper.toDomain(cmdInfo);
    }

    @Override
    public void updateCmdReceivedTime(long cId, String cmdTime) {
        cmdInfoCustomRepository.updateRcvtime(cId, cmdTime);
    }

    @Mapper(unmappedTargetPolicy= ReportingPolicy.IGNORE )
    public interface CmdInfoMapper {
        CmdInfoMapper mapper = Mappers.getMapper(CmdInfoMapper.class);

        @Mappings({
                @Mapping(target = "contractId", source = "CId"),
                @Mapping(target = "command", source = "cmd"),
                @Mapping(target = "commandTime", source = "cmdTime"),
                @Mapping(target = "receiveTime", source = "rcvTime"),
                @Mapping(target = "executeTime", source = "execTime"),
        })
        CmdInfoDomain toDomain(CmdInfo cmdInfo);

        List<CmdInfoDomain> toDomains(List<CmdInfo> cmdInfos);
    }
}
