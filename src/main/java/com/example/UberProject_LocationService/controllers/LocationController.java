package com.example.UberProject_LocationService.controllers;

import com.example.UberProject_LocationService.dto.DriverLocationDto;
import com.example.UberProject_LocationService.dto.NearByDriverRequestDto;
import com.example.UberProject_LocationService.dto.SaveDriverLocationRequestDto;
import com.example.UberProject_LocationService.services.LocationService;
import com.example.UberProject_LocationService.services.RedisLocationServiceImpl;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService)
    {
        this.locationService=locationService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<?> saveDriverLocation
            (@RequestBody SaveDriverLocationRequestDto saveDriverLocationRequestDto)
    {
        try {
            Boolean response = locationService.saveDrierLocation(saveDriverLocationRequestDto.getDriverId(),
                    saveDriverLocationRequestDto.getLatitude(),saveDriverLocationRequestDto.getLongitude());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception anyException){
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

    @PostMapping("/nearByDriver")
    public ResponseEntity<List<DriverLocationDto>>getNearByDrivers
            (@RequestBody NearByDriverRequestDto nearByDriverRequestDto) {
        try {
            List<DriverLocationDto> drivers = locationService.getNearByDrivers(nearByDriverRequestDto.getLatitude(),
                    nearByDriverRequestDto.getLongitude());
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception anyException) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
