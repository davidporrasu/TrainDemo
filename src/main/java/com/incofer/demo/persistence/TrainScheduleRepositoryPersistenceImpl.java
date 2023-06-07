package com.incofer.demo.persistence;

import com.incofer.demo.entity.TrainScheduleEntity;
import com.incofer.demo.model.Train;
import com.incofer.demo.model.TrainSchedule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Slf4j
public class TrainScheduleRepositoryPersistenceImpl implements TrainScheduleRepositoryPersistence
{
    private final EntityManager entityManager;

    @Autowired
    public TrainScheduleRepositoryPersistenceImpl(EntityManager entityManager) {
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


}
