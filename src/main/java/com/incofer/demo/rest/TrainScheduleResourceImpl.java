package com.incofer.demo.rest;

import com.incofer.demo.model.TrainSchedule;
import com.incofer.demo.services.TrainScheduleService;
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
@RequestMapping("/schedule")
public class TrainScheduleResourceImpl
{
    @Autowired

    @Qualifier("trainScheduleService")
    private TrainScheduleService trainScheduleService;
    @GetMapping("/search")
    public TrainSchedule searchSchedule(@RequestParam long id) throws Exception
    {
        return this.trainScheduleService.findById(id);
    }
    @PostMapping("/save")
    public TrainSchedule save (@RequestBody final TrainSchedule trainSchedule) throws Exception
    {
        return this.trainScheduleService.save(trainSchedule);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSchedule(@RequestParam long id) throws Exception
    {
        this.trainScheduleService.deleteSchedule(id);
        return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@RequestParam Long id, @RequestBody TrainSchedule trainSchedule)
    {
        try
        {
            this.trainScheduleService.updateSchedule(id, trainSchedule);
            return ResponseEntity.ok("Schedule updated successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
