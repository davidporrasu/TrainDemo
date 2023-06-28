package com.incofer.demo.persistence;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.entity.TrainEntity;
import com.incofer.demo.entity.TrainManagementEntity;
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
public class StationRepositoryPersistenceImpl implements StationRepositoryPersistence
{
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
    /** Named query to delete trainConsist info by train Ids */

    public void deleteByStationId(final long stationId)
    {
        log.trace("Station {} - Entered Station.delete()", stationId);

        final StationEntity entity = entityManager.find(StationEntity.class, stationId);
        if (entity != null)
        {
            entityManager.remove(entity);
        }
        else
        {
            log.trace("Station {} - No Station found for ", stationId);
        }
    }
    @Override
    public boolean persistStation(final Station station)
    {
        log.trace("Station {} - Entered persistence.persistStation()", station.getId());

        // See if this entity is already present
        final Long stationId = station.getId();
        final StationEntity managedEntity = entityManager.find(StationEntity.class, stationId);
        if (managedEntity == null)
        {

            final StationEntity newEntity = new StationEntity(stationId, station);
            this.entityManager.persist(newEntity);
        }
        else
        {

            managedEntity.setStation(station);
        }
        return true;
    }
}

