package com.incofer.demo.service;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.model.Train;
import com.incofer.demo.persistence.repository.TrainRepository;
import com.incofer.demo.persistence.TrainRepositoryPersistenceImpl;
import com.incofer.demo.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainServiceTest
{
    @Mock
    private TrainRepository trainRepository;

    @Mock
    private TrainRepositoryPersistenceImpl trainRepositoryPersistenceImpl;

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
        saveTrainEntity.setId(1L);
        saveTrainEntity.setTrain(trainToSave);
        when(trainRepository.save(Mockito.any(TrainEntity.class))).thenReturn(saveTrainEntity);
        Train savedTrain = trainService.save(trainToSave);
        verify(trainRepository, Mockito.times(1)).save(Mockito.any(TrainEntity.class));
        assertEquals(trainToSave, savedTrain);
    }

    @Test
    public void testUpdateTrain() throws Exception
    {

        TrainEntity existingTrain = new TrainEntity();
        existingTrain.setId(1L);
        existingTrain.setTrain(new Train());
        TrainEntity updatedTrain = new TrainEntity();
        updatedTrain.setTrain(new Train());
        when(trainRepository.findById(1L)).thenReturn(Optional.of(existingTrain));
        when(trainRepository.save(Mockito.any(TrainEntity.class))).thenReturn(updatedTrain);
        Train result = trainService.updateTrain(1L, updatedTrain.getTrain());
        verify(trainRepository, Mockito.times(1)).findById(1L);
        verify(trainRepository, Mockito.times(1)).save(existingTrain);
        assertEquals(updatedTrain.getTrain(), result);
    }

    @Test
    public void testDeleteTrain()
    {
        long id = 1;
        doNothing().when(trainRepository).deleteTrain(id);
        trainService.deleteTrain(id);
        verify(trainRepository, times(1)).deleteTrain(id);
    }
    @Test
    public void testSearchTrain() {
        Train findById = new Train();
        TrainEntity trainEntity = new TrainEntity();
        trainEntity.setId(1L);
        trainEntity.setTrain(findById);
        when(this.trainRepositoryPersistenceImpl.getTrain(trainEntity.getId())).thenReturn(Optional.of(trainEntity.getTrain()));
        Train result = this.trainService.findById(trainEntity.getId());
        assertEquals(result, trainEntity.getTrain());
    }

}

