package com.incofer.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.incofer.demo.enums.Status;
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
        "status",
        "currentStationId",
        "trainId",
        "trainScheduleId"

})

public class TrainManagement
{

    /**
     * SERIALIZABLE
     */
    private static final long serialVersionUID = 1L;
    /**
     * TrainManagement id
     */
    private long id;
    /**
     * Train status
     */
    private Status status;
    /**
     * CurrentStation
     */
    private long currentStationId;

    /**
     * Train id
     */
    private long trainId;

    /**
     * Train schedule id
     */
    private long trainScheduleId;

}
