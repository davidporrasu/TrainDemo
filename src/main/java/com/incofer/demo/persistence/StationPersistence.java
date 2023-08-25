package com.incofer.demo.persistence;

import com.incofer.demo.model.Station;
import lombok.NonNull;

import java.util.Optional;

public interface StationPersistence
{
    /**
     * GetStation
     *
     * @param stationId
     * @return
     */
    public Optional<Station> getStation(@NonNull final long stationId);

    public Station deleteByStationId(final long stationId);

    public Station persistStation(@NonNull final Station station);
   /* StationEntity persistStation(Station station);*/
}

