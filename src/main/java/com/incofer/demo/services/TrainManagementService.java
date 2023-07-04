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
import lombok.NonNull;
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

    @Autowired
    @Qualifier("trainScheduleRepositoryPersistenceImpl")
    private TrainScheduleRepositoryPersistence trainScheduleRepositoryPersistence;
    @Autowired
    @Qualifier("stationRepositoryPersistenceImpl")
    private StationRepositoryPersistence stationRepositoryPersistence;

    @Autowired
    @Qualifier("trainManagementRepositoryPersistenceImpl")
    private TrainManagementRepositoryPersistence trainManagementRepositoryPersistence;

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

    public TrainManagement getTrainManagement(final long id)
    {
        Optional<TrainManagement> optionalTrainManagement = this.trainManagementRepositoryPersistence.getTrainManagement(id);
        return optionalTrainManagement.get();
    }
    @Transactional
    public void deleteByTrainManagementId(long trainManagementId)
    {
        log.trace("TrainManagement {} - Entered TrainManagement.deleteByTrainManagementId()", trainManagementId);
        this.trainManagementRepositoryPersistence.deleteByTrainManagementId(trainManagementId);
    }
    @Transactional
    public TrainManagement persistTrainManagement(@NonNull final TrainManagement trainManagement)
    {
        log.trace("TrainManagement {} - Entered persistence.persistTrainManagement()", trainManagement.getId());
        this.trainManagementRepositoryPersistence.persistTrainManagement(trainManagement);
        return trainManagement;
    }

/*revisar si es necesario*/
    @Transactional
    public Station getCurrentStation(long trainManagementId)
    {
        log.info("Start getCurrentStationn id {}", trainManagementId);

        Optional<TrainManagementEntity> optionalTrainManagementEntity = this.trainManagementRepository.findById(trainManagementId);

        if (optionalTrainManagementEntity.isPresent())
        {
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

            if (optionalStation.isPresent())
            {
                Station currentStation = optionalStation.get();
                log.info("End getCurrentStation id {}: {}", trainManagementId, currentStation);
                return currentStation;
            } else
            {
                log.info("Else End getCurrentStation id {}", trainManagementId);
                throw new IllegalArgumentException("Station not found for currentStationId: " + currentStationId);
            }
        } else
        {
            log.info("Else End getCurrentStation id {}", trainManagementId);
            throw new IllegalArgumentException("TrainManagement not found for ID: " + trainManagementId);
        }
    }

    @Transactional
    public Status moveToNextStation(long trainManagementId)
    {
        log.info("Moving train to next station for trainManagementId: {}", trainManagementId);

        TrainManagementEntity trainManagementEntity = trainManagementRepository.findById(trainManagementId)
                .orElseThrow(() ->   // lo busca en el repo con el findById porque este es un metodo que ya tiene por default el jpaRepository
                {
                    String errorMessage = "TrainManagement not found for ID: " + trainManagementId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        long currentStationId = trainManagementEntity.getTrainManagement().getCurrentStationId(); // currentStationId es un long que trainManagementEntity tiene que trar pasando por el Trainmagement
        StationEntity currentStationEntity = stationRepository.findById(currentStationId) // busca el currentStationEntity en la station repo por la currentStationId
                .orElseThrow(() -> //sino error
                {
                    String errorMessage = "Station not found for currentStationId: " + currentStationId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        Status currentStatus = trainManagementEntity.getTrainManagement().getStatus(); // trae el status
        if (currentStatus.equals(Status.OUT_OF_SERVICE)) // si es igual a out of service, tira el error de abajo
        {
            String errorMessage = "Cannot move to next station, train is currently out of service";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else if (currentStatus.equals(Status.STOP)) // si es igual a stop tira el error de abajo
        {
            String errorMessage = "Cannot move to next station, train is currently stopped";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainManagementEntity.getTrainManagement().getTrainScheduleId())
                .orElseThrow(() -> // busca en el repo por id el para que trainManagementEntity pásando por le trainManagement, Traiga el TrainScheduleId
                { // sino tira el error
                    String errorMessage = "TrainSchedule not found for TrainManagement ID: " + trainManagementId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                }).getTrainSchedule();

        List<Station> stations = trainSchedule.getStations(); // trae la lista de estaciones del trainSchedule
        int currentIndex = -1; // se inicializa la variable en -1
        for (int i = 0; i < trainSchedule.getStations().size(); i++) // este for lo que hace es la variable i inicializarla en 0,
        { // la condicion i si es menor que el tamaño de la lista de estaciones el bucle sigue y se va incrementando ++ el valor de i en uno
            Station station = trainSchedule.getStations().get(i); // trae el la posicion de la estacion en la lista
            if (station.getId() == currentStationEntity.getId()) //se verifica si son iguales por el id
            {
                currentIndex = i; // para guardarlo en la variable
                break;
            }
        }
        if (currentIndex == -1) // si fuera igual a -1 ocurre el error
        {
            String errorMessage = "Invalid current station index";
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        if (currentIndex == trainSchedule.getStations().size() - 1) // si es igual al ultimo de la lista
        {
            if (currentStatus.equals(Status.MOVE))// si status es igual a move
            {
                Station lastStation = stations.get(currentIndex); // se le asigna a la ultima station el currentIndex
                if (lastStation.getActivityTypes().contains(ActivityType.TURN_POINT)) // y si esa ultima stacion tiene el getactivity
                {  // va a empezar a devolverse
                    Collections.reverse(stations);
                    trainManagementEntity.getTrainManagement().setCurrentStationId(stations.get(0).getId());// por el id se le va asignar valor 0
                    trainManagementEntity.getTrainManagement().setStatus(Status.MOVE); // le da setea el status move
                    trainManagementRepository.save(trainManagementEntity); // se envia a guardar al repo
                    log.info("Train has reached the last station with TURN_POINT activity type. Returning to the initial station by passing through all other stations in reverse order.");
                } else // sino se le setea el status stop y el tren quedaria parado en la ultima station
                {
                    trainManagementEntity.getTrainManagement().setStatus(Status.STOP);
                    trainManagementRepository.save(trainManagementEntity);
                    log.info("Train has reached the last station without TURN_POINT activity type. Stopping the train.");
                }
            } else
            {
                trainManagementEntity.getTrainManagement().setStatus(Status.STOP);
                trainManagementRepository.save(trainManagementEntity);
                log.info("Train has reached the last station. Stopping the train.");
            }
        } else     // sino esta es stop
        {

            Station nextStation = trainSchedule.getStations().get(currentIndex + 1); // se le asigna a nextStation el Schedule y el indice
            long nextStationId = nextStation.getId(); // se trae el id de la siguiente estacion

            trainManagementEntity.getTrainManagement().setCurrentStationId(nextStationId); // el id lo setea a la nextStationId
            trainManagementRepository.save(trainManagementEntity); // y lo guarda
            log.info("Train moved to the next station successfully.");
        }

        Station firstStation = trainSchedule.getStations().get(0);  // busca la primera station
        if (trainManagementEntity.getTrainManagement().getCurrentStationId() == firstStation.getId() // si la station actual es igual a la primera station
                && trainManagementEntity.getTrainManagement().getStatus() != Status.ORIGIN) // sino tiene status dif a Origin es que no ha llegado
        {
            trainManagementEntity.getTrainManagement().setStatus(Status.ORIGIN); // se le setae el status origin
            trainManagementRepository.save(trainManagementEntity); // se guarda en el repo y el tren retornará a la inicial station
            log.info("Train has returned to the origin station. Sending a message.");
        }

        return trainManagementEntity.getTrainManagement().getStatus(); // retorna el status del tren
    }

    public static int getAdvancedKm(final List<Station> stations, final Station currentStation, boolean turnPoint)
    {
        int total = 0; // se inicia en variable total en 0
        for (Station station : stations) // bucle que recorre las stations
        {
            total = total + station.getKm(); // se indica que total es igual a la suma de los km de la station
            if (station.equals(currentStation) && turnPoint == false) // si la station currentstation
            { // no se encuentra un turnpoint
                break; // sigue
            }
        }
        //boolean lastStation = currentStation.getActivityTypes().contains(ActivityType.TURN_POINT)
        if (turnPoint) // si hay un turn point
        {
            final int size = stations.size(); // el tamaño de la lista
            for (int i = size - 2; i > -1; i--) // i (índice) igual al tamaño lista - dos, si i es  menor que -1, va a decrecer de uno en uno
            {
                final Station station = stations.get(i); // se asigna a la estacion la posicion
                final Station previousStation = stations.get(i + 1); // se asigna a la station previa la posicion siguiente
                total = total + previousStation.getKm(); // al total se le suma el km de la siguiente station
                if (station.equals(currentStation)) // si la estacion es igual a la estacio actual
                {
                    break; // corta
                }
            }
        }
        return total; // retoran el total
    }

    public List<Station> getStations(long trainManagementId)
    {
        TrainManagementEntity trainManagementEntity = trainManagementRepository.findById(trainManagementId)
                .orElseThrow(() ->   // lo busca en el repo con el findById porque este es un metodo que ya tiene por default el jpaRepository
                {
                    String errorMessage = "TrainManagement not found for ID: " + trainManagementId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });


        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainManagementEntity.getTrainManagement().getTrainScheduleId())
                .orElseThrow(() ->
                {
                    String errorMessage = "TrainSchedule not found for TrainManagement ID: " + trainManagementId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                }).getTrainSchedule();
        long currentStationId = trainManagementEntity.getTrainManagement().getCurrentStationId(); // currentStationId es un long que trainManagementEntity tiene que trar pasando por el Trainmagement
        StationEntity currentStationEntity = stationRepository.findById(currentStationId) // busca el currentStationEntity en la station repo por la currentStationId
                .orElseThrow(() -> //sino error
                {
                    String errorMessage = "Station not found for currentStationId: " + currentStationId;
                    log.error(errorMessage);
                    throw new IllegalArgumentException(errorMessage);
                });

        List<Station> stations = trainSchedule.getStations(); // trae la lista de estaciones del trainSchedule
        int currentIndex = -1; // se inicializa la variable en -1
        for (int i = 0; i < trainSchedule.getStations().size(); i++) // este for lo que hace es la variable i inicializarla en 0,
        { // la condicion i si es menor que el tamaño de la lista de estaciones el bucle sigue y se va incrementando ++ el valor de i en uno
            Station station = trainSchedule.getStations().get(i); // trae el la posicion de la estacion en la lista
            if (station.getId() == currentStationEntity.getId()) //se verifica si son iguales por el id
            {
                currentIndex = i; // para guardarlo en la variable
                break;
            }
        }
        if (currentIndex == -1) // si fuera igual a -1 ocurre el error
        {
            String errorMessage = "Invalid current station index";
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
        return stations;
    }
}
