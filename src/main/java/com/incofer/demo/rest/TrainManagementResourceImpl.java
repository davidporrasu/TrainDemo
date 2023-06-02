package com.incofer.demo.rest;

import com.incofer.demo.enums.Status;
import com.incofer.demo.model.Station;
import com.incofer.demo.model.TrainManagement;
import com.incofer.demo.services.TrainManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/trainManagement")
public class TrainManagementResourceImpl
{
    @Autowired
    @Qualifier("trainManagementService")
    private TrainManagementService trainManagementService;

    @GetMapping("/currentStation")
    public ResponseEntity<Station> getCurrentStation(@RequestParam long trainManagementId)
    {
        log.info("Start Train management id {}", trainManagementId);
        try
        {
            Station currentStation = trainManagementService.getCurrentStation(trainManagementId);
            return ResponseEntity.ok(currentStation);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.notFound().build();
        }
        finally
        {
            log.info("End Train management id {}", trainManagementId);
        }
    }

    @PostMapping("/save")
    public TrainManagement save(@RequestBody final TrainManagement trainManagement) throws Exception
    {
        return this.trainManagementService.save(trainManagement);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTrainManagement(@RequestParam long id) throws Exception
    {
        this.trainManagementService.deleteTrainManagement(id);
        return new ResponseEntity<>("TrainManagement deleted successfully", HttpStatus.OK);
    }
    @PostMapping("/trainManagement/moveToNextStation")
    public ResponseEntity<Status> moveToNextStation(@RequestParam long trainManagementId)
    {
        try
        {
            Status status = trainManagementService.moveToNextStation(trainManagementId);
            return ResponseEntity.ok(status);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Error moving train to next station: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}