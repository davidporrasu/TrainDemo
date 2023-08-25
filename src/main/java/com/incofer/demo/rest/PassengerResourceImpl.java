package com.incofer.demo.rest;

import com.incofer.demo.model.Passenger;
import com.incofer.demo.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passengers")
public class PassengerResourceImpl implements PassengerResource
{
    @Autowired
    @Qualifier("passengerService")
    private PassengerService passengerService;

    @Override
    public Passenger save(@RequestBody final Passenger passenger) throws Exception
    {
        this.passengerService.save(passenger);
        return passenger;
    }

}
