package com.incofer.demo.services;

import com.incofer.demo.entity.StationEntity;
import com.incofer.demo.entity.TrainManagementEntity;
import com.incofer.demo.enums.ActivityType;
import com.incofer.demo.enums.Status;
import com.incofer.demo.model.Station;
import com.incofer.demo.model.TrainManagement;
import com.incofer.demo.model.TrainSchedule;
import com.incofer.demo.persistence.StationRepositoryPersistence;
import com.incofer.demo.persistence.TrainScheduleRepositoryPersistence;
import com.incofer.demo.persistence.repository.StationRepository;
import com.incofer.demo.persistence.repository.TrainManagementRepository;
import com.incofer.demo.persistence.TrainManagementRepositoryPersistence;
import com.incofer.demo.persistence.repository.TrainScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("trainManagementService")
public class TrainManagementService
{

    /*-----------nuevo-----------*/
    @Autowired
    @Qualifier("trainScheduleRepositoryPersistenceImpl")
    private TrainScheduleRepositoryPersistence trainScheduleRepositoryPersistence;
    @Autowired
    @Qualifier("stationRepositoryPersistenceImpl")
    private StationRepositoryPersistence stationRepositoryPersistence;

    @Autowired
    @Qualifier("trainManagementRepositoryPersistenceImpl")
    private TrainManagementRepositoryPersistence trainManagementRepositoryPersistence;
    /*-----------nuevo-----------*/

    @Autowired
    private TrainManagementRepository trainManagementRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private TrainScheduleRepository trainScheduleRepository;

    public TrainManagementService(TrainManagementRepositoryPersistence trainManagementRepositoryPersistence)
    {
        this.trainManagementRepositoryPersistence = trainManagementRepositoryPersistence;
    }

    public TrainManagement getTrainManagement(final long id) {
        Optional<TrainManagement> optionalTrainManagement = this.trainManagementRepositoryPersistence.getTrainManagement(id);
        return optionalTrainManagement.get();
    }
    public TrainManagement deleteByTrainManagementId(long trainManagementId) {
        log.trace("TrainManagement {} - Entered TrainManagement.deleteByTrainManagementId()", trainManagementId);
        this.trainManagementRepositoryPersistence.deleteByTrainManagementId(trainManagementId);
        return null;
    }

    public TrainManagement persistTrainManagement(TrainManagement trainManagement) {
        log.trace("TrainManagement {} - Entered persistence.persistTrainManagement()", trainManagement.getId());
        final Long trainManagementId = trainManagement.getId();
        return null;
    }



/*revisar si es necesario*/
    public Station getCurrentStation(long trainManagementId)
    {
        log.info("Start getCurrentStation id {}", trainManagementId);

        Optional<TrainManagementEntity> optionalTrainManagementEntity = this.trainManagementRepository.findById(trainManagementId);

        if (optionalTrainManagementEntity.isPresent())
        {
            TrainManagementEntity trainManagementEntity = optionalTrainManagementEntity.get();
            long currentStationId = trainManagementEntity.getTrainManagement().getCurrentStationId();
            TrainSchedule trainSchedule = trainScheduleRepository.findById(trainManagementEntity.getTrainManagement().getTrainScheduleId())
                    .orElseThrow(() ->
                    {
                        String errorMessage = "TrainSchedulee not found for TrainManagement ID: " + trainManagementId;
                        log.error(errorMessage);
                        throw new IllegalArgumentException(errorMessage);
                    }).getTrainSchedule();

            Optional<Station> optionalStation = trainSchedule.getStations().stream()
                    .filter(station -> station.getId() == currentStationId)
                    .findFirst();

            if (optionalStation.isPresent())
            {
                Station currentStation = optionalStation.get();
                log.info("End getCurrentStation id {}: {}", trainManagementId, currentStation);
                return currentStation;
            }
            else
            {
                log.info("Else End getCurrentStation id {}", trainManagementId);
                throw new IllegalArgumentException("Station not found for currentStationId: " + currentStationId);
            }
        }
        else
        {
            log.info("Else End getCurrentStation id {}", trainManagementId);
            throw new IllegalArgumentException("TrainManagement not found for ID: " + trainManagementId);
        }
    }

    @Transactional
    public Status moveToNextStation(long trainManagementId) {
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
        if (currentStatus.equals(Status.OUT_OF_SERVICE)) {
            String errorMessage = "Cannot move to next station, train is currently out of service";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else if (currentStatus.equals(Status.STOP)) {
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
        for (int i = 0; i < trainSchedule.getStations().size(); i++) {
            Station station = trainSchedule.getStations().get(i);
            if (station.getId() == currentStationEntity.getId()) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            String errorMessage = "Invalid current station index";
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        if (currentIndex == trainSchedule.getStations().size() - 1) {
            if (currentStatus.equals(Status.MOVE)) {
                Station lastStation = stations.get(currentIndex);
                if (lastStation.getActivityTypes().contains(ActivityType.TURN_POINT)) {
                    Collections.reverse(stations);
                    trainManagementEntity.getTrainManagement().setCurrentStationId(stations.get(0).getId());
                    trainManagementEntity.getTrainManagement().setStatus(Status.MOVE);
                    trainManagementRepository.save(trainManagementEntity);
                    log.info("Train has reached the last station with TURN_POINT activity type. Returning to the initial station by passing through all other stations in reverse order.");
                } else {
                    trainManagementEntity.getTrainManagement().setStatus(Status.STOP);
                    trainManagementRepository.save(trainManagementEntity);
                    log.info("Train has reached the last station without TURN_POINT activity type. Stopping the train.");
                }
            } else {
                trainManagementEntity.getTrainManagement().setStatus(Status.STOP);
                trainManagementRepository.save(trainManagementEntity);
                log.info("Train has reached the last station. Stopping the train.");
            }
        }
        else {
            // Si no es la última estación, avanzar a la siguiente estación
            Station nextStation = trainSchedule.getStations().get(currentIndex + 1);
            long nextStationId = nextStation.getId();

            trainManagementEntity.getTrainManagement().setCurrentStationId(nextStationId);
            trainManagementRepository.save(trainManagementEntity);
            log.info("Train moved to the next station successfully.");
        }

        return trainManagementEntity.getTrainManagement().getStatus();
    }
}