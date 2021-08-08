package com.rental.api.dataprovider.database.maria.repository.impl;

import com.rental.api.dataprovider.database.maria.entity.ClientVersionInfo;
import com.rental.api.dataprovider.database.maria.repository.ClientVersionCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ClientVersionInfoCustomRepositoryImpl implements ClientVersionCustomRepository {

    private final EntityManager entityManager;

    @Override
    public ClientVersionInfo findOne() {
        return entityManager.createQuery("SELECT C FROM ClientVersionInfo AS C ORDER BY versionId DESC", ClientVersionInfo.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
