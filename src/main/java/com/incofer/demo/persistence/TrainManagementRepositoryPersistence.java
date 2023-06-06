package com.incofer.demo.persistence;

import com.incofer.demo.model.Train;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TrainManagementRepositoryPersistence {
    /**
     * GetTrainManagement
     * @param trainManagementId
     * @return
     */
    public Optional<Train> getTrainManagement(@NonNull final long trainManagementId);
    public Optional<Train> getTrain(@NonNull final long trainId);

    /* en el trainManagementRepository hay dos variables que se borran y actualizan, train y trainManagement*/
}
