package com.incofer.demo.persistence;

import com.incofer.demo.model.Train;
import lombok.NonNull;

import java.util.Optional;
public interface TrainRepositoryPersistence
{
    /**
     * GetTrain
     * @param trainId
     * @return
     */
    public Optional<Train> getTrain(@NonNull final long trainId);

    /** Named query to delete trainConsist info by train Ids */
    Long DELETE_TRAIN_BY_IDS = Long.valueOf("DELETE_TRAIN_BY_IDS");
}