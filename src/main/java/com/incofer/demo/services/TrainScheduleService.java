package com.incofer.demo.services;

import com.incofer.demo.entity.TrainScheduleEntity;
import com.incofer.demo.model.TrainSchedule;
import com.incofer.demo.persistence.repository.TrainScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("trainScheduleService")
public class TrainScheduleService
{
    @Autowired
    private TrainScheduleRepository trainScheduleRepository;

    public TrainSchedule findById(final long id)
    {
        TrainScheduleEntity entity = this.trainScheduleRepository.getById(id);
        return entity.getTrainSchedule();
    }

    @Transactional
    public void deleteSchedule(final long id)
    {
        this.trainScheduleRepository.deleteSchedule(id);
    }

    @Transactional
    public TrainSchedule save(final TrainSchedule save)
    {
        TrainScheduleEntity trainScheduleEntity = TrainScheduleEntity.builder()
                .trainSchedule(save)
                .build();
        return this.trainScheduleRepository.save(trainScheduleEntity).getTrainSchedule();
    }

    public TrainSchedule updateSchedule(final long id, TrainSchedule updateSchedule) throws Exception
    {
        Optional<TrainScheduleEntity> optionalTrainSchedule = trainScheduleRepository.findById(id);
        if (optionalTrainSchedule.isPresent())
        {
            TrainScheduleEntity existingTrainScheduleEntity = optionalTrainSchedule.get();
            existingTrainScheduleEntity.setTrainSchedule(updateSchedule);

            TrainScheduleEntity saveTrainSchedule = trainScheduleRepository.save(existingTrainScheduleEntity);
            return saveTrainSchedule.getTrainSchedule();
        }
        else
        {
            throw new Exception("\"No train schedule found with ID: " + id);
        }
    }

    public TrainScheduleEntity getById(final long id)
    {
        return this.trainScheduleRepository.getById(id);
    }
}
