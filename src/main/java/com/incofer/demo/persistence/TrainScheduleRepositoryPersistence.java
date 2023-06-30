package com.incofer.demo.persistence;

import com.incofer.demo.model.TrainSchedule;
import lombok.NonNull;

import java.util.Optional;

public interface TrainScheduleRepositoryPersistence
{
    /**
     * GetTrainSchedule
     * @param TrainScheduleId
     * @return
     */
    public Optional<TrainSchedule> getTrainSchedule(@NonNull final long TrainScheduleId);

    public void deleteByTrainScheduleId(final long trainScheduleId);

    /**
     * Method to Create or Update CpTrainConsist in the database
     */

    public boolean persistTrainSchedule(final TrainSchedule trainSchedule);
}
