package com.example.UberProject_LocationService.services;

import com.example.UberProject_LocationService.dto.DriverLocationDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisLocationServiceImpl implements LocationService {

    private static final String DRIVER_GEO_OPS_KEY="drivers";
    private static final Double searchRadius =5.0;

    private final StringRedisTemplate stringRedisTemplate;

    public RedisLocationServiceImpl(StringRedisTemplate stringRedisTemplate)
    {
        this.stringRedisTemplate =stringRedisTemplate;
    }

    @Override
    public Boolean saveDrierLocation(String driverId, Double latitude, Double longitude) {
        GeoOperations<String, String> geoOperations = stringRedisTemplate.opsForGeo();
        geoOperations.add(DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<>(driverId,
                        new Point(latitude,
                                longitude)));
        return true;
    }

    @Override
    public List<DriverLocationDto> getNearByDrivers(Double latitude, Double longitude) {

        GeoOperations<String, String> geoOperations = stringRedisTemplate.opsForGeo();
        Distance distance = new Distance(searchRadius, Metrics.KILOMETERS);
        Circle within = new Circle(new Point(latitude, longitude), distance);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                geoOperations.radius(DRIVER_GEO_OPS_KEY, within);

        List<DriverLocationDto> drivers = new ArrayList<>();
        assert results != null;
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            Point point =geoOperations.position(DRIVER_GEO_OPS_KEY,result.getContent().getName()).get(0);
            DriverLocationDto driverLocationDto = DriverLocationDto.builder()
                    .driverId(result.getContent().getName())
                    .latitude(point.getX())
                    .longitude(point.getY()).build();  ;
            drivers.add(driverLocationDto);
        }
        return drivers;
    }
}
