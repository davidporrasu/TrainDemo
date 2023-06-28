package com.incofer.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.incofer.demo.entity.TrainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "id",
        "name",
        "category",
        "Engine",
        "wagons",
        "passengerCapacity",
        "workerCapacity",
        "velocity"
})

public class Train
{
        /**
         * SERIALIZABLE
         */
        private static final long serialVersionUID = 1L;
        /**
         * Train id
         */
        private long id;
        /**
         * Train name
         */
        private String name;
        /**
         * Train Category
         */
        private String category;
        /**
         * Train Engine
         */
        private int Engine;
        /**
         * Train wagon
         */
        private int wagons;
        /**
         * PassengerCapacity
         */
        private int passengerCapacity;
        /**
         * WorkerCapacity
         */
        private int workerCapacity;
        /**
         * Velocity
         */
        private float velocity;


}





