package com.incofer.demo.service;

import com.incofer.demo.model.Station;
import com.incofer.demo.persistence.TrainManagementRepositoryPersistenceImpl;
import com.incofer.demo.services.TrainManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrainManagementServiceTest
{
    @Mock
    private TrainManagementRepositoryPersistenceImpl trainManagementRepositoryPersistenceImpl;

    @Autowired
    private TrainManagementService trainManagementService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAdvancedKm_TurnPointFalse_UntilStation5()
    {
        List<Station> stations = new ArrayList<>();
        Station station1 = Station.builder().id(1).km(0).build();
        Station station2 = Station.builder().id(2).km(5).build();
        Station station3 = Station.builder().id(3).km(10).build();
        Station station4 = Station.builder().id(4).km(15).build();
        Station station5 = Station.builder().id(5).km(20).build();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
        int result = TrainManagementService.getAdvancedKm(stations, station5, false);
        assertEquals(50, result);
    }

    @Test
    public void testGetAdvancedKm_TurnPointFalse_UntilStation3()
    {
        List<Station> stations = new ArrayList<>();
        Station station1 = Station.builder().id(1).km(0).build();
        Station station2 = Station.builder().id(2).km(5).build();
        Station station3 = Station.builder().id(3).km(10).build();
        Station station4 = Station.builder().id(4).km(15).build();
        Station station5 = Station.builder().id(5).km(20).build();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
        int result = TrainManagementService.getAdvancedKm(stations, station3, false);
        assertEquals(15, result);
    }

    @Test
    public void testGetAdvancedKm_TurnPointTrue_UntilStation3()
    {
        List<Station> stations = new ArrayList<>();
        Station station1 = Station.builder().id(1).km(0).build();
        Station station2 = Station.builder().id(2).km(5).build();
        Station station3 = Station.builder().id(3).km(10).build();
        Station station4 = Station.builder().id(4).km(15).build();
        Station station5 = Station.builder().id(5).km(20).build();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
        int result = TrainManagementService.getAdvancedKm(stations, station3, true);
        assertEquals(85, result);
    }

    @Test
    public void testGetAdvancedKm_TurnPointTrue_UntilStation2()
    {
        List<Station> stations = new ArrayList<>();
        Station station1 = Station.builder().id(1).km(0).build();
        Station station2 = Station.builder().id(2).km(5).build();
        Station station3 = Station.builder().id(3).km(10).build();
        Station station4 = Station.builder().id(4).km(15).build();
        Station station5 = Station.builder().id(5).km(20).build();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
        int result = TrainManagementService.getAdvancedKm(stations, station2, true);
        assertEquals(95, result);
    }

    @Test
    public void testGetAdvancedKm_TurnPointTrue_UntilStation1()
    {
        List<Station> stations = new ArrayList<>();
        Station station1 = Station.builder().id(1).km(0).build();
        Station station2 = Station.builder().id(2).km(5).build();
        Station station3 = Station.builder().id(3).km(10).build();
        Station station4 = Station.builder().id(4).km(15).build();
        Station station5 = Station.builder().id(5).km(20).build();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
        int result = TrainManagementService.getAdvancedKm(stations, station1, true);
        assertEquals(100, result);
    }
    @Test
    public void testGetAdvancedKm_TurnPointTrue_UntilStation1and1()
    {
        List<Station> stations = new ArrayList<>();
        Station station1 = Station.builder().id(1).km(0).build();
        Station station2 = Station.builder().id(2).km(5).build();
        Station station3 = Station.builder().id(3).km(10).build();
        Station station4 = Station.builder().id(4).km(15).build();
        Station station5 = Station.builder().id(5).km(20).build();
        stations.add(station5);
        stations.add(station4);
        stations.add(station3);
        stations.add(station2);
        stations.add(station1);
        int result = TrainManagementService.getAdvancedKm(stations, station1, true);
        assertEquals(80, result);
    }

}
