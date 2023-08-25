package com.incofer.demo.services;

import com.incofer.demo.model.Train;
import com.incofer.demo.persistence.TrainPersistence;
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
    @Qualifier("trainPersistenceImpl")
    private TrainPersistence trainPersistence;

    public Train getTrain(final String id)
    {
        Optional<Train> optionalTrain = this.trainPersistence.getTrain(id);
        return optionalTrain.get();
    }

    @Transactional
    public void deleteTrain(final String id)
    {
        this.trainPersistence.deleteByTrainId(id);
    }

    @Transactional
    public boolean persistTrain(final Train save)
    {
        return this.trainPersistence.persistTrain(save);
    }
}


