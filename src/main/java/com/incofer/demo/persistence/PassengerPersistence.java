package com.incofer.demo.persistence;

import com.incofer.demo.model.Passenger;
import lombok.NonNull;

public interface PassengerPersistence
{
    public boolean save(@NonNull final Passenger passenger);
}