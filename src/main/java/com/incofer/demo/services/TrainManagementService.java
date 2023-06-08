package com.incofer.demo.services;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.entity.TrainManagementEntity;
import com.incofer.demo.enums.ActivityType;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("trainManagementService")
public class TrainManagementService {
    @Autowired
    private TrainManagementRepository trainManagementRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private TrainScheduleRepository trainScheduleRepository;

    public TrainManagementService(TrainManagementRepository trainManagementRepository) {
        this.trainManagementRepository = trainManagementRepository;
    }

    public TrainManagement findById(final long id) {
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

    public Station getCurrentStation(long trainManagementId) {
        log.info("Start getCurrentStation id {}", trainManagementId);

        Optional<TrainManagementEntity> optionalTrainManagementEntity = this.trainManagementRepository.findById(trainManagementId);

        if (optionalTrainManagementEntity.isPresent()) {
            TrainManagementEntity trainManagementEntity = optionalTrainManagementEntity.get();
            long currentStationId = trainManagementEntity.getTrainManagement().getCurrentStationId();
            TrainSchedule trainSchedule = trainScheduleRepository.findById(trainManagementEntity.getTrainManagement().getTrainScheduleId())
                    .orElseThrow(() ->
                    {
                        String errorMessage = "TrainSchedule not found for TrainManagement ID: " + trainManagementId;
                        log.error(errorMessage);
                        throw new IllegalArgumentException(errorMessage);
                    }).getTrainSchedule();

            Optional<Station> optionalStation = trainSchedule.getStations().stream()
                    .filter(station -> station.getId() == currentStationId)
                    .findFirst();

            if (optionalStation.isPresent()) {
                Station currentStation = optionalStation.get();
                log.info("End getCurrentStation id {}: {}", trainManagementId, currentStation);
                return currentStation;
            } else {
                log.info("Else End getCurrentStation id {}", trainManagementId);
                throw new IllegalArgumentException("Station not found for currentStationId: " + currentStationId);
            }
        } else {
            log.info("Else End getCurrentStation id {}", trainManagementId);
            throw new IllegalArgumentException("TrainManagement not found for ID: " + trainManagementId);
        }
    }

    @Transactional
    public Status moveToNextStation(long trainManagementId)
    {
        log.info("Moving train to next station for trainManagementId: {}", trainManagementId);

        TrainManagementEntity trainManagementEntity = trainManagementRepository.findById(trainManagementId)
                .orElseThrow(() -> {
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

        Status currentStatus = trainManagementEntity.getTrainManagement().getStatus();
        if (currentStatus.equals(Status.OUT_OF_SERVICE))
        {
            String errorMessage = "Cannot move to next station, train is currently out of service";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else if (currentStatus.equals(Status.STOP))
        {
            String errorMessage = "Cannot move to next station, train is currently stopped";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainManagementEntity.getTrainManagement().getTrainScheduleId())
                .orElseThrow(() ->
                {
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
            throw new IllegalStateException(errorMessage);
        }

        Station lastStation = trainSchedule.getStations().get(currentIndex + 1);
        long lastStationId = lastStation.getId();

        // Verificar si es la última estación
        if (currentIndex + 1 == trainSchedule.getStations().size() - 1)
        {
            if (lastStation.getActivityTypes().contains(ActivityType.TURN_POINT))
            {
                // Estación final con actividad de "turn point" - Recorrido inverso
                trainManagementEntity.getTrainManagement().setCurrentStationId(lastStationId);
                trainManagementEntity.getTrainManagement().setStatus(Status.MOVE);
                trainManagementRepository.save(trainManagementEntity);
                log.info("Train has reached the last station with a turn point activity. Reversing the route.");

                // Obtener la lista de estaciones en orden inverso
                List<Station> reverseStations = new ArrayList<>(stations);
                Collections.reverse(reverseStations);

                // Actualizar el índice actual para el recorrido inverso
                currentIndex = reverseStations.indexOf(lastStation);

                // Establecer la nueva estación siguiente para el recorrido inverso
                lastStation = reverseStations.get(currentIndex + 1);
                lastStationId = lastStation.getId();
            } else {
                // Estación final sin actividad de "turn point" - Parar el tren
                trainManagementEntity.getTrainManagement().setStatus(Status.STOP);
                log.info("Train has reached the last station. No turn point activity found. Stopping the train.");
            }
        } else {
            // No es la última estación - Continuar moviéndose
            trainManagementEntity.getTrainManagement().setStatus(Status.MOVE);
            log.info("Train will move to the next station");
        }

        // Actualizar la estación actual en el TrainManagementEntity
        trainManagementEntity.getTrainManagement().setCurrentStationId(lastStationId);
        trainManagementRepository.save(trainManagementEntity);

        // Si la última estación tiene una actividad de "turn point", hacer el recorrido inverso
        if (currentIndex + 1 == trainSchedule.getStations().size() - 1 && lastStation.getActivityTypes().contains(ActivityType.TURN_POINT)) {
            trainManagementEntity.getTrainManagement().setCurrentStationId(currentStationId);
            trainManagementEntity.getTrainManagement().setStatus(Status.MOVE);
            trainManagementRepository.save(trainManagementEntity);
            log.info("Train has reached the last station with a turn point activity. Returning to the previous station.");

            // Restaurar la lista de estaciones en orden original
            currentIndex = stations.indexOf(lastStation);

            // Establecer la nueva estación siguiente para el recorrido inverso
            lastStation = stations.get(currentIndex + 1);
            lastStationId = lastStation.getId();
        }

        log.info("Train moved to the next station successfully");

        return trainManagementEntity.getTrainManagement().getStatus();
    }
}

