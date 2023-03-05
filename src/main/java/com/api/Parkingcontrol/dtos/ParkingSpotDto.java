package com.api.Parkingcontrol.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotDto {
    @NotBlank
    private String parkingSpotNumber;
    @NotBlank
    @Size(max = 7)
    private String licensePlateCar;
    @NotBlank
    private String modelcar;
    @NotBlank
    private String brandCar;
    @NotBlank
    private String colorCar;
    @NotBlank
    private String responsibleName; 
    @NotBlank
    private String apartment;
    @NotBlank
    private String block;

}
