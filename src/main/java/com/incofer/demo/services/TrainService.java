package com.incofer.demo.services;

import com.incofer.demo.model.Train;
import com.incofer.demo.persistence.TrainRepositoryPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service("trainService")
public class TrainService
{
    @Autowired
    @Qualifier("trainRepositoryPersistenceImpl")
    private TrainRepositoryPersistence trainRepositoryPersistence;

    public Train getTrain(final String id)
    {
        Optional<Train> optionalTrain = this.trainRepositoryPersistence.getTrain(id);
        return optionalTrain.get();
    }

    @Transactional
    public void deleteTrain(final String id)
    {
        this.trainRepositoryPersistence.deleteByTrainId(id);
    }

    @Transactional
    public boolean persistTrain(final Train save)
    {
        return this.trainRepositoryPersistence.persistTrain(save);
    }

}


