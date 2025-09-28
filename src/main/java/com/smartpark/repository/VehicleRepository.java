package com.smartpark.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartpark.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
	
	List<Vehicle> findByParkingLot_LotId(String lotId);

}
