package com.api.Parkingcontrol.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.Parkingcontrol.models.ParkingSpotModel;

@Repository
public interface ParkingSpotRepository extends CrudRepository<ParkingSpotModel,Long>{
    boolean existsByLicensePlateCar(String licensePlateCar);
    boolean existsByParkingSpotNumber(String parkingSoptNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);
    Page<ParkingSpotModel> findAll(Pageable pageable);
}
