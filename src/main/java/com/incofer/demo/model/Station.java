package com.incofer.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.incofer.demo.enums.ActivityType;
import com.incofer.demo.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "id",
        "city",
        "km",
        "activityTypes"
})

public class Station
{
    /**
     * Station id
     */
    private long id;
    private City city;
    private int km;
    List<ActivityType> activityTypes;
}
