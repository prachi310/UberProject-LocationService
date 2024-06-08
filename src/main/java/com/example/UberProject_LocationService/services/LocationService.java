package com.example.UberProject_LocationService.services;

import com.example.UberProject_LocationService.dto.DriverLocationDto;

import java.util.List;

public interface LocationService {

    Boolean saveDrierLocation(String driverId,Double latitude,Double longitude);

    List<DriverLocationDto> getNearByDrivers(Double latitude,Double longitude);

}
