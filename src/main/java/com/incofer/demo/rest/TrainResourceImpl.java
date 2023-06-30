package com.incofer.demo.rest;

import com.incofer.demo.model.Train;
import com.incofer.demo.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trains")

public class TrainResourceImpl implements TrainResource
{
    @Autowired

    @Qualifier("trainService")
    private TrainService trainService;

    @GetMapping("/search")
    public Train searchTrain(@RequestParam long id) throws Exception
    {
        return this.trainService.getTrain(String.valueOf(id));
    }

    @Override
    public Train searchTrainV2(final String id) throws Exception
    {
        return this.trainService.getTrain(id);

    }

    @Override
    public Train save(final Train train) throws Exception
    {
        this.trainService.persistTrain(train);
        return train;
    }


    @Override
    public ResponseEntity<String> deleteTrainV2(final String id) throws Exception
    {
        this.trainService.deleteTrain(id);
        return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteTrain(final long id) throws Exception
    {
        this.trainService.deleteTrain(String.valueOf(id));
        return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateTrain(long id, Train train)
    {
        try
        {
            this.trainService.persistTrain(train);
            return ResponseEntity.ok("Train updated successfully");
        } catch (Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @Override
    public ResponseEntity<?> updateTrainV2(String id, Train train)
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

