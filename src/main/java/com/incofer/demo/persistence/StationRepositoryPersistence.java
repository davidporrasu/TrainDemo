package com.incofer.demo.persistence;

import com.incofer.demo.model.Station;
import lombok.NonNull;

import java.util.Optional;

public interface StationRepositoryPersistence {

    /**
     * GetStation
     * @param stationId
     * @return
     */
    public Optional<Station> getStation(@NonNull final long stationId);
}
