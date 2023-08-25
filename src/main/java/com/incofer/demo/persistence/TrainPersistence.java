package com.incofer.demo.persistence;

import com.incofer.demo.model.Train;
import lombok.NonNull;

import java.util.Optional;
public interface TrainPersistence
{
    /**
     * GetTrain
     * @param trainId
     * @return
     */

   public Optional<Train> getTrain(@NonNull final String trainId);

    /** Named query to delete trainConsist info by train Ids */
    public void deleteByTrainId(final String trainId);

    /**
     * Method to Create or Update CpTrainConsist in the database
     */

    public boolean persistTrain(final Train train);
}

