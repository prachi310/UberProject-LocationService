package com.example.UberProject_LocationService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearByDriverRequestDto {

    Double latitude;
    Double longitude;
}
