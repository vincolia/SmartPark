package com.smartpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartpark.entity.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, String>{

}
