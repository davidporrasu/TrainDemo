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

@Repository("trainManagementPersistenceImpl")
@Slf4j
public class TrainManagementPersistenceImpl implements TrainManagementPersistence
{
    private final EntityManager entityManager;

    @Autowired
    public TrainManagementPersistenceImpl(EntityManager entityManager)
    {
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
        } else
        {
            log.info("Exiting TrainManagementRepositoryImpl with no TrainManagement {}", trainManagementId);
            return Optional.empty();
        }
    }

    public Optional<Train> getTrain(final String trainId)
    {
        log.info("In TrainRepositoryImpl getTrain {}", trainId);
        final TrainEntity trainEntity = this.entityManager.find(TrainEntity.class, trainId);
        if (trainEntity != null)
        {
            log.info("ExitingTrainRepositoryImpl with Train {}", trainId);
            return Optional.of(trainEntity.getTrain());
        } else
        {
            log.info("Exiting TrainRepositoryImpl with no Train {}", trainId);
            return Optional.empty();
        }
    }

    /**
     * Named query to delete trainConsist info by train Ids
     */

    public void deleteByTrainManagementId(final long trainManagementId)
    {
        log.trace("TrainManagement {} - Entered TrainManagement.delete()", trainManagementId);

        final TrainManagementEntity entity = entityManager.find(TrainManagementEntity.class, trainManagementId);
        if (entity != null)
        {
            entityManager.remove(entity);
        } else
        {
            log.trace("TrainManagement {} - No TrainManagement found for ", trainManagementId);
        }
    }

    public void deleteByTrainId(final long trainId)
    {
        log.trace("Train {} - Entered Train.delete()", trainId);

        final TrainEntity entity = entityManager.find(TrainEntity.class, trainId);
        if (entity != null)
        {
            entityManager.remove(entity);
        } else
        {
            log.trace("Train {} - No Train found for ", trainId);
        }
    }

    public boolean persistTrainManagement(@NonNull final TrainManagement trainManagement)
    {
        log.trace("TrainManagement {} - Entered persistence.persistTrainManagement()", trainManagement.getId());

        // See if this entity is already present
        final long trainManagementId = trainManagement.getId();
        final TrainManagementEntity managedEntity = entityManager.find(TrainManagementEntity.class, trainManagementId);

        if (managedEntity == null)
        {
            final TrainManagementEntity newEntity = new TrainManagementEntity();
            newEntity.setTrainManagement(trainManagement);
            this.entityManager.persist(newEntity);
        }
        else
        {
            managedEntity.setTrainManagement(trainManagement);
        }
        return true;
    }
}
