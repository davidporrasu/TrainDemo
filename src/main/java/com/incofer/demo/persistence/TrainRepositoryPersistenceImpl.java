package com.incofer.demo.persistence;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.model.Train;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Slf4j
public class TrainRepositoryPersistenceImpl implements TrainRepositoryPersistence
{
    private final EntityManager entityManager;

    @Autowired
    public TrainRepositoryPersistenceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * GetTrain
     * @param trainId
     * @return
     */
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