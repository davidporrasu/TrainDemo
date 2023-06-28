package com.incofer.demo.persistence;

import com.incofer.demo.model.Train;
import com.incofer.demo.model.TrainManagement;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TrainManagementRepositoryPersistence extends TrainRepositoryPersistence{
    /**
     * GetTrainManagement
     * @param trainManagementId
     * @return
     */
    public Optional<TrainManagement> getTrainManagement(@NonNull final long trainManagementId);
    public Optional<Train> getTrain(@NonNull final long trainId);

    /** Named query to delete trainConsist info by train Ids */
    public void deleteByTrainManagementId(final long trainManagementId);

    public boolean persistTrain(final Train train);
}
