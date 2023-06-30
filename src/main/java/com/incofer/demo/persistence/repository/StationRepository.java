package com.incofer.demo.persistence.repository;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StationRepository extends JpaRepository<StationEntity, Long>
{
    StationEntity save(Station station);

    @Transactional
    @Modifying
    @Query("DELETE FROM StationEntity t WHERE t.id = :id")
    void deleteStation(long id);

    @Override
    StationEntity getById(Long aLong);

    @Transactional
    @Modifying
    @Query("UPDATE StationEntity t SET t.station = :station WHERE t.id = :id")
    void updateStation(long id, StationEntity station);

}

