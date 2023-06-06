package com.incofer.demo.persistence;

import com.incofer.demo.model.Train;
import lombok.NonNull;

import java.util.Optional;

public interface TrainScheduleRepositoryPersistence {
    /**
     * GetSchedule
     * @param scheduleId
     * @return
     */
    public Optional<Train> getSchedule(@NonNull final long scheduleId);
}
