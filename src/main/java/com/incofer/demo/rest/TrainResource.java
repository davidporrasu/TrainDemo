/*
 * © 2021 Wabtec Corporation. All Rights Reserved.
 */
package com.incofer.demo.rest;

import com.incofer.demo.model.Train;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource class for Identify Train
 */
@RequestMapping("/trains")
public interface TrainResource
{
    /**
     * searchTrain
     * @param id value
     * @return Train value
     * @throws Exception exception
     */
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Train searchTrain(@RequestParam long id) throws Exception;
}

