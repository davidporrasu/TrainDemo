package com.incofer.demo.persistence;

import com.incofer.demo.model.Train;
import com.incofer.demo.model.TrainSchedule;
import lombok.NonNull;

import java.util.Optional;

public interface TrainScheduleRepositoryPersistence {
    /**
     * GetTrainSchedule
     * @param TrainScheduleId
     * @return
     */
    public Optional<TrainSchedule> getTrainSchedule(@NonNull final long TrainScheduleId);

    Long DELETE_TRAIN_SCHEDULE_BY_IDS = Long.valueOf("DELETE_TRAIN_SCHEDULE_BY_IDS");

    /**
     * Method to Create or Update CpTrainConsist in the database
     */

    boolean persistTrainSchedule(TrainSchedule trainSchedule);
}
