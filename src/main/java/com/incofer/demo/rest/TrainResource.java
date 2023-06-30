/*
 * © 2021 Wabtec Corporation. All Rights Reserved.
 */
package com.incofer.demo.rest;

import com.incofer.demo.model.Train;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource class for Identify Train comment
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
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    Train searchTrain(@RequestParam long id) throws Exception;
}

