package com.incofer.demo.services;

import com.incofer.demo.model.TrainSchedule;
import com.incofer.demo.persistence.TrainSchedulePersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j

@Service("trainScheduleService")
public class TrainScheduleService
{
    @Autowired
    @Qualifier("trainSchedulePersistenceImpl")
    private TrainSchedulePersistence trainSchedulePersistence;


    public TrainSchedule getTrainSchedule(final long id)
    {
        Optional<TrainSchedule> optionalTrainSchedule = this.trainSchedulePersistence.getTrainSchedule(id);
        return optionalTrainSchedule.get();
    }
    @Transactional
    public TrainSchedule deleteByTrainScheduleId(long trainScheduleId)
    {
        log.trace("TrainSchedule {} - Entered TrainSchedule.deleteByTrainScheduleId()", trainScheduleId);
        this.trainSchedulePersistence.deleteByTrainScheduleId(trainScheduleId);
        return null;
    }

    @Transactional
    public boolean persistTrainSchedule(TrainSchedule trainSchedule)
    {
        log.trace("TrainSchedule {} - Entered persistence.persistTrainSchedule()", trainSchedule.getId());
        return this.trainSchedulePersistence.persistTrainSchedule(trainSchedule);
    }
}
