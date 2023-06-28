package com.incofer.demo.services;

import com.incofer.demo.model.Train;
import com.incofer.demo.persistence.TrainRepositoryPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service("trainService")
public class TrainService
{
    @Autowired
    @Qualifier("trainRepositoryPersistenceImpl")
    private TrainRepositoryPersistence trainRepositoryPersistence;


    public Train getTrain(final long id) {
        Optional<Train> optionalTrain = this.trainRepositoryPersistence.getTrain(id);
        return optionalTrain.get();
    }
    public Train deleteByTrainId(long trainId) {
    log.trace("Train {} - Entered Train.deleteByTrainId()", trainId);
    this.trainRepositoryPersistence.deleteByTrainId(trainId);
        return null;
    }

    public Train persistTrain(Train train) {
        log.trace("Train {} - Entered persistence.persistTrain()", train.getId());
        final Long trainId = train.getId();
        return null;
    }
}

