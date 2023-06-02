package com.incofer.demo.services;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.model.Station;
import com.incofer.demo.persistence.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("stationService")
public class StationService
{
    @Autowired
    private StationRepository stationRepository;

    public Station findById(final long id)
    {
        StationEntity entity = this.stationRepository.getById(id);
        return entity.getStation();
    }

    @Transactional
    public void deleteStation(final long id)
    {
        this.stationRepository.deleteStation(id);
    }

    @Transactional
    public Station save(final Station save)
    {
        StationEntity stationEntity = StationEntity.builder()
                .station(save)
                .build();
        return this.stationRepository.save(stationEntity).getStation();
    }
    public Station updateStation(final long id, Station updateStation) throws Exception
    {
        Optional<StationEntity> optionalStation = stationRepository.findById(id);
        if (optionalStation.isPresent())
        {
            StationEntity existingStationEntity = optionalStation.get();
            existingStationEntity.setStation(updateStation);

           StationEntity saveStation = stationRepository.save(existingStationEntity);
            return saveStation.getStation();
        }
        else
        {
            throw new Exception("\"No station found with ID: " + id);
        }
    }

    public StationEntity getById(final long id)
    {
        return this.stationRepository.getById(id);

    }
}
