package com.incofer.demo.services;

import com.incofer.demo.model.Station;
import com.incofer.demo.model.TrainSchedule;
import com.incofer.demo.persistence.StationRepositoryPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service("stationService")
public class StationService {

    @Autowired
    @Qualifier("stationRepositoryPersistenceImpl")
    private StationRepositoryPersistence stationRepositoryPersistence;


    public Station getStation(final long id) {
        Optional<Station> optionalStation = this.stationRepositoryPersistence.getStation(id);
        return optionalStation.get();
    }
    public TrainSchedule deleteByStationId(long stationId) {
        log.trace("Station {} - Entered Station.deleteByStationId()", stationId);
        this.stationRepositoryPersistence.deleteByStationId(stationId);
        return null;
    }

    public Station persistStation(Station station) {
        log.trace("Station {} - Entered persistence.persistStation()", station.getId());
        final Long stationId = station.getId();
        return null;
    }

}


