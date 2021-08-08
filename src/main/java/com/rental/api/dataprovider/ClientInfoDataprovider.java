package com.rental.api.dataprovider;

import com.rental.api.core.domain.ClientVersionInfoDomain;
import com.rental.api.core.domain.CmdInfoDomain;
import com.rental.api.core.usecase.client.IClientInfoDataprovider;
import com.rental.api.core.usecase.cmd.ICmdInfoDataprovider;
import com.rental.api.dataprovider.database.maria.entity.ClientVersionInfo;
import com.rental.api.dataprovider.database.maria.entity.CmdInfo;
import com.rental.api.dataprovider.database.maria.repository.ClientVersionCustomRepository;
import com.rental.api.dataprovider.database.maria.repository.ClientVersionRepository;
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
public class ClientInfoDataprovider implements IClientInfoDataprovider {

    private final ClientVersionRepository clientVersionRepository;

    @Override
    public ClientVersionInfoDomain getLatestClientVersionInfo() {
        ClientVersionInfo clientVersionInfo = clientVersionRepository.findTopByOrderByUpdateTimeDesc();
        if (Objects.isNull(clientVersionInfo)){
            return null;
        }

        return ClientInfoMapper.mapper.toDomain(clientVersionInfo);
    }

    @Mapper(unmappedTargetPolicy= ReportingPolicy.IGNORE )
    public interface ClientInfoMapper {
        ClientInfoMapper mapper = Mappers.getMapper(ClientInfoMapper.class);

        ClientVersionInfoDomain toDomain(ClientVersionInfo clientVersionInfo);
    }
}
