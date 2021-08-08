package com.rental.api.dataprovider.database.maria.repository.impl;

import com.rental.api.dataprovider.database.maria.entity.CmdInfo;
import com.rental.api.dataprovider.database.maria.repository.CmdInfoCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CmdInfoCustomRepositoryImpl implements CmdInfoCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<CmdInfo> findByCId(long cId) {
        return entityManager.createQuery("SELECT C FROM CmdInfo AS C WHERE C.cId=:cId AND (rcvTime IS NULL or rcvTime = '') and (execTime IS NOT NULL and execTime <> '' and execTime <= NOW()) ORDER BY cmdTime DESC", CmdInfo.class)
                .setParameter("cId", cId)
                .getResultList();
    }

    @Override
    public CmdInfo findOneByCId(long cId) {
        return entityManager.createQuery("SELECT C FROM CmdInfo AS C WHERE C.cId=:cId ORDER BY cmdTime DESC", CmdInfo.class)
                .setParameter("cId", cId)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public void updateRcvtime(long cId, String cmdTime) {
        entityManager.createQuery("UPDATE CmdInfo AS C SET rcvTime=NOW() WHERE C.cId=:cId AND C.cmdTime=:cmdTime")
                .setParameter("cId", cId)
                .setParameter("cmdTime", cmdTime)
                .executeUpdate();
    }
}
