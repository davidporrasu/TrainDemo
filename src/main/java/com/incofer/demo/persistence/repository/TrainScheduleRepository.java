package com.incofer.demo.persistence.repository;

import com.incofer.demo.entity.TrainScheduleEntity;
import com.incofer.demo.model.TrainManagement;
import com.incofer.demo.model.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TrainScheduleRepository extends JpaRepository<TrainScheduleEntity, Long>
{
    TrainScheduleEntity save(TrainSchedule trainSchedule);

    @Transactional
    @Modifying
    @Query("UPDATE TrainManagementEntity t SET t.trainManagement = :trainManagement WHERE t.id = :id")
    void moveTrainToNextStation(@Param("id") long id, @Param("trainManagement") TrainManagement trainManagement);

    @Override
    TrainScheduleEntity getById(Long aLong);

    @Transactional
    @Modifying
    @Query("UPDATE TrainScheduleEntity t SET t.trainSchedule = :trainSchedule WHERE t.id = :id")
    void updateSchedule(long id, TrainScheduleEntity trainSchedule);
    @Transactional
    @Modifying
    @Query("DELETE FROM TrainScheduleEntity t WHERE t.id = :id")
    void deleteSchedule(long id);
}
