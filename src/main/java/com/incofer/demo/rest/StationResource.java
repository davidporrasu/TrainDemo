/*
 * © 2023 Train Corporation. All Rights Reserved.
 */
package com.incofer.demo.rest;

import com.incofer.demo.model.Station;
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
 * Resource class for Identify Station
 */
@RequestMapping("/stations")
public interface StationResource
{

    /**
     * searchStation
     * @param id value
     * @return Station value
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
    Station searchStation(@RequestParam long id) throws Exception;

    /**
     * saveStation
     * @param station value
     * @return Station value
     * @throws Exception exception
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    Station save(@RequestBody final Station station) throws Exception;

    /**
     * deleteStation
     * @param id value
     * @return Station value
     * @throws Exception exception
     */
    @DeleteMapping(value ="/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<?> deleteStationById(@RequestParam final long id) throws Exception;


    /**
     * updateStation
     * @param id value
     * @return Station value
     * @throws Exception exception
     */
    @PutMapping(value ="/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Example API endpoint")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    ResponseEntity<?> updateStation(@RequestParam Long id, @RequestBody Station station ) throws Exception;

}
