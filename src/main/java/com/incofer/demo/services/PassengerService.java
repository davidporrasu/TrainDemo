package com.incofer.demo.services;

import com.incofer.demo.model.Passenger;
import com.incofer.demo.persistence.PassengerPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service("passengerService")
public class PassengerService
{
    @Autowired
    @Qualifier("passengerPersistenceImpl")
    private PassengerPersistence passengerPersistence;

    @Transactional
    public Passenger save(Passenger passenger)
    {
        this.passengerPersistence.save(passenger);
        return passenger;

    }
}
