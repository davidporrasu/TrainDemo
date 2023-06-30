/*
 * © 2023 Train Corporation. All Rights Reserved.
 */
package com.incofer.demo.rest;

import com.incofer.demo.model.Train;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * searchTrain
     * @param id value
     * @return Train value
     * @throws Exception exception
     */
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE + ";version=2")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    Train searchTrainV2(@RequestParam final String id) throws Exception;


    /**
     * saveTrain
     * @param train value
     * @return Train value
     * @throws Exception exception
     */
    @PostMapping(value = "/persistTrain", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    Train save(@RequestBody final Train train) throws Exception;

    /**
     * deleteTrain
     * @param id value
     * @return  Train value
     * @throws Exception exception
     */
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<String> deleteTrain(@RequestParam final long id) throws Exception;
    /**
     * deleteTrain
     * @param id value
     * @return  Train value
     * @throws Exception exception
     */
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE + ";version=2")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<String> deleteTrainV2(@RequestParam final String id) throws Exception;
    /**
     * updateTrain
     * @param id value
     * @return  Train value
     * @throws Exception exception
     */

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<?> updateTrain(@RequestParam long id, @RequestBody Train train)throws Exception;

    /**
     * updateTrain
     * @param id value
     * @return  Train value
     * @throws Exception exception
     */

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE + ";version=2")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<?> updateTrainV2(@RequestParam final String id, @RequestBody Train train)throws Exception;
}

