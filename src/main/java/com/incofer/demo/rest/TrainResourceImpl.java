package com.incofer.demo.rest;

import com.incofer.demo.model.Train;
import com.incofer.demo.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TrainResourceImpl implements TrainResource
{
    @Autowired

    @Qualifier("trainService")
    private TrainService trainService;

    @Override
    public Train searchTrain(final long id) throws Exception
    {
        return this.trainService.findById(id);

    }

    @PostMapping("/save")
    public Train save (@RequestBody final Train train) throws Exception
    {
        return this.trainService.save(train);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTrain(@RequestParam long id) throws Exception
    {
        this.trainService.deleteTrain(id);
        return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrain(@RequestParam Long id, @RequestBody Train train)
    {
        try
        {
            this.trainService.updateTrain(id, train);
            return ResponseEntity.ok("Train updated successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}

