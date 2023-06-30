package com.incofer.demo.rest;

import com.incofer.demo.model.Station;
import com.incofer.demo.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stations")
public class StationResourceImpl implements StationResource
{
    @Autowired

    @Qualifier("stationService")
    private StationService stationService;


    @Override
    public Station searchStation(@RequestParam long id) throws Exception
    {
        return this.stationService.getStation(id);
    }

    @Override
    public Station save(@RequestBody final Station station) throws Exception
    {
        this.stationService.persistStation(station);
        return station;
    }

    @Override
    public ResponseEntity<?> deleteStationById(@RequestParam long id) throws Exception
    {
        this.stationService.deleteByStationId(id);
        return new ResponseEntity<>("Station deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateStation(@RequestParam Long id, @RequestBody Station station)
    {
        try
        {
            this.stationService.persistStation(station);
            return ResponseEntity.ok("Station updated successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}