package com.incofer.demo.persistence.repository;

import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TrainRepository extends JpaRepository<TrainEntity, Long>
{

    TrainEntity save(Train train);

    @Transactional
    @Modifying
    @Query("DELETE FROM TrainEntity t WHERE t.id = :id")
    void deleteTrain(long id);

    @Override
    TrainEntity getById(Long aLong);

    @Transactional
    @Modifying
    @Query("UPDATE TrainEntity t SET t.train = :train WHERE t.id = :id")
    void updateTrain(long id, TrainEntity train);
}


