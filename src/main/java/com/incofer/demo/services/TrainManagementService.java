package com.incofer.demo.services;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.entity.TrainManagementEntity;
import com.incofer.demo.enums.Status;
import com.incofer.demo.model.Station;
import com.incofer.demo.model.TrainManagement;
import com.incofer.demo.model.TrainSchedule;
import com.incofer.demo.persistence.repository.StationRepository;
import com.incofer.demo.persistence.repository.TrainManagementRepository;
import com.incofer.demo.persistence.repository.TrainScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("trainManagementService")
public class TrainManagementService
{
    @Autowired
    private TrainManagementRepository trainManagementRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private TrainScheduleRepository trainScheduleRepository;
    public TrainManagementService(TrainManagementRepository trainManagementRepository)
    {
        this.trainManagementRepository = trainManagementRepository;
    }

    public TrainManagement findById(final long id)
    {
        TrainManagementEntity entity = this.trainManagementRepository.getById(id);
        return entity.getTrainManagement();
    }

    @Transactional
    public TrainManagement save(final TrainManagement save) {
        TrainManagementEntity trainManagementEntity = TrainManagementEntity.builder()
                .trainManagement(save)
                .build();
        return this.trainManagementRepository.save(trainManagementEntity).getTrainManagement();
    }

    @Transactional
    public void deleteTrainManagement(final long id) {
        this.trainManagementRepository.deleteTrainManagement(id);
    }

    public Station getCurrentStation(long trainManagementId)
    {
        log.info("Start getCurrentStationId id {}", trainManagementId);

        Optional<TrainManagementEntity> optionalTrainManagementEntity = this.trainManagementRepository.findById(trainManagementId);

        if (optionalTrainManagementEntity.isPresent())
        {
            TrainManagementEntity trainManagementEntity = optionalTrainManagementEntity.get();
            long currentStationId = trainManagementEntity.getTrainManagement().getCurrentStationId();
            Optional<StationEntity> optionalStation = this.stationRepository.findById(currentStationId);

            if (optionalStation.isPresent())
            {
                StationEntity existingStationEntity = optionalStation.get();
                return existingStationEntity.getStation();
            }
            else
            {
                log.info("Else End getCurrentStationId id {}", trainManagementId);
                throw new IllegalArgumentException("Station not found for currentStationId: " + currentStationId);
            }
        }
        else
        {
            log.info("Else End getCurrentStationId id {}", trainManagementId);
            throw new IllegalArgumentException("TrainManagement not found for ID: " + trainManagementId);
        }

    }

    @Transactional
    public Status moveToNextStation(long trainManagementId)
    {
        log.info("Moving train to next station for trainManagementId: {}", trainManagementId);

        TrainManagementEntity trainManagementEntity = trainManagementRepository.findById(trainManagementId)
                .orElseThrow(() ->
                {
                    String errorMessage = "TrainManagement not found for ID: " + trainManagementId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        long currentStationId = trainManagementEntity.getTrainManagement().getCurrentStationId();
        StationEntity currentStationEntity = stationRepository.findById(currentStationId)
                .orElseThrow(() -> {
                    String errorMessage = "Station not found for currentStationId: " + currentStationId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        // Verificar el estado actual del tren
        Status currentStatus = trainManagementEntity.getTrainManagement().getStatus();
        if (currentStatus.equals(Status.OUT_OF_SERVICE))
        {
            String errorMessage = "Cannot move to next station, train is currently out of service";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        else if (currentStatus.equals(Status.STOP))
        {
            String errorMessage = "Cannot move to next station, train is currently stopped";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainManagementEntity.getTrainManagement().getTrainScheduleId())
                .orElseThrow(() -> {
                    String errorMessage = "TrainSchedule not found for TrainManagement ID: " + trainManagementId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                }).getTrainSchedule();

        List<Station> stations = trainSchedule.getStations();
        int currentIndex = -1;
        for (int i = 0; i < trainSchedule.getStations().size(); i++)
        {
            Station station = trainSchedule.getStations().get(i);
            if (station.getId() == currentStationEntity.getId())
            {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1 || currentIndex == trainSchedule.getStations().size() - 1)
        {
            String errorMessage = "Cannot move to next station, already at the last station";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Station nextStation = trainSchedule.getStations().get(currentIndex + 1);
        long nextStationId = nextStation.getId();

        // Actualiza la estación actual en el TrainManagementEntity
        trainManagementEntity.getTrainManagement().setCurrentStationId(nextStationId);
        trainManagementEntity.getTrainManagement().setStatus(Status.MOVE);
        trainManagementRepository.save(trainManagementEntity);

        log.info("Train moved to the next station successfully");

        // Retorna el nuevo estado del tren
        return trainManagementEntity.getTrainManagement().getStatus();
    }
}