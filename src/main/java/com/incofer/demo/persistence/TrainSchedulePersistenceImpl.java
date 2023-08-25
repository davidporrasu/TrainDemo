package com.incofer.demo.persistence;

import com.incofer.demo.entity.TrainScheduleEntity;
import com.incofer.demo.model.TrainSchedule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("trainSchedulePersistenceImpl")

@Slf4j
public class TrainSchedulePersistenceImpl implements TrainSchedulePersistence
{
    private final EntityManager entityManager;

    @Autowired
    public TrainSchedulePersistenceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    /**
     * GetTrain
     * @param trainScheduleId
     * @return
     */
    public Optional<TrainSchedule> getTrainSchedule(@NonNull final long trainScheduleId)
    {
        log.info("In TrainScheduleRepositoryImpl getTrainSchedule {}", trainScheduleId);
        final TrainScheduleEntity trainScheduleEntity = this.entityManager.find(TrainScheduleEntity.class, trainScheduleId);
        if (trainScheduleEntity != null)
        {
            log.info("ExitingTrainScheduleRepositoryImpl with TrainSchedule {}", trainScheduleId);
            return Optional.of(trainScheduleEntity.getTrainSchedule());
        }
        else
        {
            log.info("Exiting TrainScheduleRepositoryImpl with no TrainSchedule {}", trainScheduleId);
            return Optional.empty();
        }
    }
    /** Named query to delete trainConsist info by train Ids */

    public void deleteByTrainScheduleId(final long trainScheduleId)
    {
        log.trace("TrainSchedule {} - Entered TrainSchedule.delete()", trainScheduleId);

        final TrainScheduleEntity entity = entityManager.find(TrainScheduleEntity.class, trainScheduleId);
        if (entity != null)
        {
            entityManager.remove(entity);
        }
        else
        {
            log.trace("TrainSchedule {} - No TrainSchedule found for ", trainScheduleId);
        }
    }

    @Override
    public boolean persistTrainSchedule(final TrainSchedule trainSchedule)
    {
        log.trace("TrainSchedule {} - Entered persistence.persistTrainSchedule()", trainSchedule.getId());

        // See if this entity is already present
        final long trainScheduleId = trainSchedule.getId();
        final TrainScheduleEntity managedEntity = entityManager.find(TrainScheduleEntity.class, trainScheduleId);
        if (managedEntity == null)
        {
            // if not, create the entity and persist!
            final TrainScheduleEntity newEntity = TrainScheduleEntity.builder().trainSchedule(trainSchedule).build();
            this.entityManager.persist(newEntity);
        }
        else
        {
            // if it is, just update the managed entity; (will be committed by JPA framework)
            managedEntity.setTrainSchedule(trainSchedule);
        }
        return true;
    }
}
