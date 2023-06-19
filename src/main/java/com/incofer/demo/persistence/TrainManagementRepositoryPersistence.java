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

    /* en el trainManagementRepository hay dos variables que se borran y actualizan, train y trainManagement*/

    /** Named query to delete trainConsist info by train Ids */
    Long DELETE_TRAIN_MANAGEMENT_BY_IDS = Long.valueOf("DELETE_TRAIN_MANAGEMENT_BY_IDS");
    boolean persistTrain(Train train);
}
