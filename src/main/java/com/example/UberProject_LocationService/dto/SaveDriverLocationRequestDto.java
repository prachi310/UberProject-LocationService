package com.example.UberProject_LocationService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveDriverLocationRequestDto {

    String driverId;
    Double latitude;
    Double longitude;

}
