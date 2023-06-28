package com.incofer.demo.rest;

import com.incofer.demo.model.Station;
import com.incofer.demo.model.Train;
import com.incofer.demo.services.StationService;
import com.incofer.demo.services.TrainService;
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
@RequestMapping("/trains")

public class TrainResourceImpl
{
    @Autowired

    @Qualifier("trainService")
    private TrainService trainService;
    @GetMapping("/search")
    public Train searchTrain(@RequestParam long id) throws Exception
    {
        return this.trainService.getTrain(id);
    }

    @PostMapping("/save")
    public Train persistStation(@RequestBody final Train train) throws Exception
    {
        this.trainService.persistTrain(train);
        return train;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteByTrainId(@RequestParam long id) throws Exception {
        this.trainService.deleteByTrainId(id);
        return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> persistTrain(@RequestParam Long id, @RequestBody Train train)
    {
        try
        {
            this.trainService.persistTrain(train);
            return ResponseEntity.ok("Train updated successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}

