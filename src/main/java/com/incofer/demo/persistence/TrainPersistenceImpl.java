package com.incofer.demo.persistence;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.model.Train;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("trainPersistenceImpl")
@Slf4j
public class TrainPersistenceImpl implements TrainPersistence
{
    private final EntityManager entityManager;

    @Autowired
    public TrainPersistenceImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * GetTrain
     * @param trainId
     * @return
     */
    public Optional<Train> getTrain(final String trainId)
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
    /** Named query to delete trainConsist info by train Ids */

    public void deleteByTrainId(final String trainId)
    {
        log.trace("Train {} - Entered Train.delete()", trainId);

        final TrainEntity entity = entityManager.find(TrainEntity.class, trainId);
        if (entity != null)
        {
            entityManager.remove(entity);
        }
        else
        {
            log.trace("Train {} - No Train found for ", trainId);
        }
    }

    @Override
    public boolean persistTrain(final Train train)
    {
        log.trace("Train {} - Entered persistence.persistTrain()", train.getId());

        // See if this entity is already present
        final String trainId = train.getId();
        final TrainEntity managedEntity = entityManager.find(TrainEntity.class, trainId);
        if (managedEntity == null)
        {
            // if not, create the entity and persist!
            final TrainEntity newEntity = new TrainEntity(trainId, train);
            this.entityManager.persist(newEntity);
        }
        else
        {
            // if it is, just update the managed entity; (will be committed by JPA framework)
            managedEntity.setTrain(train);
        }
        return true;
    }
}