package com.incofer.demo.services;

import com.incofer.demo.model.Station;
import com.incofer.demo.persistence.StationPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service("stationService")
public class StationService
{
    @Autowired
    @Qualifier("stationPersistenceImpl")
    private StationPersistence stationPersistence;


    public Station getStation(final long id)
    {
        Optional<Station> optionalStation = this.stationPersistence.getStation(id);
        return optionalStation.get();
    }
    @Transactional
    public Station deleteByStationId(long stationId)
    {
        log.trace("Station {} - Entered Station.deleteByStationId()", stationId);
        //this.stationRepositoryPersistence.deleteByStationId(stationId);
        return this.stationPersistence.deleteByStationId(stationId);
    }

    public Station persistStation(Station station)
    {
        log.trace("Station {} - Entered persistence.persistStation()", station.getId());
        return this.stationPersistence.persistStation(station);
    }
}


