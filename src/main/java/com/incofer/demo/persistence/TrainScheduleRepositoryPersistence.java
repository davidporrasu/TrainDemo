package com.incofer.demo.persistence;

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
}
