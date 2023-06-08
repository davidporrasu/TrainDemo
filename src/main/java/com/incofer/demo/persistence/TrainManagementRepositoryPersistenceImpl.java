package com.incofer.demo.persistence;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.entity.TrainManagementEntity;
import com.incofer.demo.model.Train;
import com.incofer.demo.model.TrainManagement;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Slf4j
public class TrainManagementRepositoryPersistenceImpl implements TrainManagementRepositoryPersistence {
    private final EntityManager entityManager;

    @Autowired
    public TrainManagementRepositoryPersistenceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<TrainManagement> getTrainManagement(@NonNull final long trainManagementId)
    {
        log.info("In TrainManagementRepositoryImpl getTrainManagement {}", trainManagementId);
        final TrainManagementEntity trainManagementEntity = this.entityManager.find(TrainManagementEntity.class, trainManagementId);
        if (trainManagementEntity != null)
        {
            log.info("ExitingTrainManagementRepositoryImpl with TrainManagement {}", trainManagementId);
            return Optional.of(trainManagementEntity.getTrainManagement());
        }
        else
        {
            log.info("Exiting TrainManagementRepositoryImpl with no TrainManagement {}", trainManagementId);
            return Optional.empty();
        }
    }

    public Optional<Train> getTrain(@NonNull final long trainId)
    {
        log.info("In TrainRepositoryImpl getTrain {}", trainId);
        final TrainEntity trainEntity = this.entityManager.find(TrainEntity.class, trainId);
        if (trainEntity != null)
        {
            log.info("ExitingTrainRepositoryImpl with Train {}", trainId);
            return Optional.of(trainEntity.getTrain());
        }
        else
        {
            log.info("Exiting TrainRepositoryImpl with no Train {}", trainId);
            return Optional.empty();
        }
    }
}
