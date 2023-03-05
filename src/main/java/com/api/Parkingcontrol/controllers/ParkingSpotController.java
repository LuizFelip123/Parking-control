package com.api.Parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.Parkingcontrol.dtos.ParkingSpotDto;
import com.api.Parkingcontrol.models.ParkingSpotModel;
import com.api.Parkingcontrol.services.ParkingSpotService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot/")
public class ParkingSpotController {
    
    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
               return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : License Plate Car is Already i use!"); 
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : Parking  Spot  is already in use!");
        }
        if(parkingSpotService.existsByApartamentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : Parking  Spot  already registered for this Apartment/block!");
        }
        
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<Object> getAllParkingSpots(@PageableDefault(page = 0, size = 10 , sort = "id",  direction = Sort.Direction.ASC)Pageable pageable ){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }
    @GetMapping("{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") Long id){
        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
        if(!parkingSpotOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotOptional.get());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePrakingSpot(@PathVariable(value = "id") Long id){
        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
        if(!parkingSpotOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        } 
        parkingSpotService.deletePrakingSpot(parkingSpotOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updatePrakingSpot(@PathVariable(value = "id") Long id, @RequestBody  @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotOptional = parkingSpotService.findById(id);
        if(!parkingSpotOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found.");
        }
        var parkingSpotModel = parkingSpotOptional.get();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(id);
        parkingSpotModel.setRegistrationDate(parkingSpotOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}
