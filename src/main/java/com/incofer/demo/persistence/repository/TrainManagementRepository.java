package com.incofer.demo.persistence.repository;

import com.incofer.demo.entity.TrainManagementEntity;
import com.incofer.demo.model.TrainManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TrainManagementRepository extends JpaRepository<TrainManagementEntity, Long>
{

    TrainManagementEntity save(TrainManagement trainManagement);

    @Transactional
    @Modifying
    @Query("DELETE FROM TrainManagementEntity t WHERE t.id = :id")
    void deleteTrain(long id);

    @Override
    TrainManagementEntity getById(Long aLong);

    @Transactional
    @Modifying
    @Query("UPDATE TrainManagementEntity t SET t.trainManagement = :trainManagement WHERE t.id = :id")
    void updateTrainManagement(long id, TrainManagementEntity trainManagement);

    @Transactional
    @Modifying
    @Query("DELETE FROM TrainManagementEntity t WHERE t.id = :id")
    void deleteTrainManagement(long id);

}



