package com.incofer.demo.persistence;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.model.Station;
import com.incofer.demo.model.Train;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Slf4j
public class StationRepositoryPersistenceImpl implements StationRepositoryPersistence {
    private final EntityManager entityManager;

    @Autowired
    public StationRepositoryPersistenceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * GetStation
     * @param stationId
     * @return
     */
    public Optional<Station> getStation(@NonNull final long stationId)
    {
        log.info("In StationRepositoryImpl getStation {}", stationId);
        final StationEntity stationEntity = this.entityManager.find(StationEntity.class, stationId);
        if (stationEntity != null)
        {
            log.info("ExitingStationRepositoryImpl with Station {}", stationId);
            return Optional.of(stationEntity.getStation());
        }
        else
        {
            log.info("Exiting StationRepositoryImpl with no Station {}", stationId);
            return Optional.empty();
        }
    }
}

