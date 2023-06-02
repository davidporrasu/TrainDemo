package com.incofer.demo.services;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.model.Train;
import com.incofer.demo.persistence.TrainRepositoryPersistence;
import com.incofer.demo.persistence.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("trainService")
public class TrainService
{
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainRepositoryPersistence trainRepositoryPersistence;

    public Train findById(final long id)
    {
        Optional<Train> optionalTrain = this.trainRepositoryPersistence.getTrain(id);
        return optionalTrain.get();
    }

    @Transactional
    public void deleteTrain(final long id)
    {
        this.trainRepository.deleteTrain(id);
    }


    @Transactional
    public Train save(final Train save)
    {
        TrainEntity trainEntity = TrainEntity.builder()
                .train(save)
                .build();
        return this.trainRepository.save(trainEntity).getTrain();
    }

    public Train updateTrain(final long id, Train updateTrain) throws Exception
    {
        Optional<TrainEntity> optionalTrain = trainRepository.findById(id);
        if (optionalTrain.isPresent())
        {
            TrainEntity existingTrainENtity = optionalTrain.get();
            //existingTrainENtity.setId(updateTrain.getId());
            existingTrainENtity.setTrain(updateTrain);

            TrainEntity saveTrain = trainRepository.save(existingTrainENtity);
            return saveTrain.getTrain();
        }
        else
        {
            throw new Exception("\"No train found with ID: " + id);
        }
    }
}

