package com.incofer.demo.rest;

import com.incofer.demo.model.Station;
import com.incofer.demo.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stations")
public class StationResourceImpl
{
    @Autowired

    @Qualifier("stationService")
    private StationService stationService;
    @GetMapping("/search")
    public Station searchStation(@RequestParam long id) throws Exception
    {
        return this.stationService.findById(id);
    }
    @PostMapping("/save")
    public Station save (@RequestBody final Station station) throws Exception
    {
        return this.stationService.save(station);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStation(@RequestParam long id) throws Exception
    {
        this.stationService.deleteStation(id);
        return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStation(@RequestParam Long id, @RequestBody Station station)
    {
        try
        {
            this.stationService.updateStation(id, station);
            return ResponseEntity.ok("Station updated successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
