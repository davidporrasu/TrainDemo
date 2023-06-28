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
    public void deleteByTrainId(final long trainId);

    /**
     * Method to Create or Update CpTrainConsist in the database
     */

    public boolean persistTrain(final Train train);
}
