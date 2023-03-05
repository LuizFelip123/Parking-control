package com.api.Parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.Parkingcontrol.models.ParkingSpotModel;
import com.api.Parkingcontrol.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {
    @Autowired
    private  ParkingSpotRepository parkingSpotRepository;
    
    @Transactional
    public  ParkingSpotModel save(ParkingSpotModel parkingSpotModel){
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartamentAndBlock(String apartment, String block) {
        return  parkingSpotRepository.existsByApartmentAndBlock(apartment, block) ;
    }

    public Page findAll(Pageable pageable) {
        
        return parkingSpotRepository.findAll(pageable);
    } 

    public Optional<ParkingSpotModel> findById(Long id) {
        System.out.println(id);
        return parkingSpotRepository.findById(id); 
    }

    @Transactional
    public void deletePrakingSpot(ParkingSpotModel parkingSpotModel) {
      parkingSpotRepository.delete(parkingSpotModel);
    }
}
