package com.incofer.demo.service;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.model.Train;
import com.incofer.demo.persistence.TrainRepositoryPersistence;
import com.incofer.demo.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrainServiceTest
{

    @Mock
    private TrainRepositoryPersistence trainRepositoryPersistence;

    @InjectMocks
    private TrainService trainService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testSaveTrain()
    {
        Train trainToSave = new Train();
        trainToSave.setName("New Train");
        TrainEntity saveTrainEntity = new TrainEntity();
        saveTrainEntity.setId((String.valueOf(1L)));
        saveTrainEntity.setTrain(trainToSave);
        when(trainRepositoryPersistence.persistTrain(trainToSave)).thenReturn(true);
        boolean result = trainService.persistTrain(trainToSave);
        //verify(trainRepository, Mockito.times(1)).save(Mockito.any(TrainEntity.class));
        assertEquals(true, result);
    }

    @Test
    public void testSearchTrain() {
        Train findById = new Train();
        TrainEntity trainEntity = new TrainEntity();
        trainEntity.setId("1");
        trainEntity.setTrain(findById);
        when(this.trainRepositoryPersistence.getTrain(trainEntity.getId())).thenReturn(Optional.of(trainEntity.getTrain()));
        Train result = this.trainService.getTrain(trainEntity.getId());
        assertEquals(result, trainEntity.getTrain());
    }
}

