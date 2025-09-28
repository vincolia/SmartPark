package com.smartpark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartpark.dto.ParkingLotDto;
import com.smartpark.entity.ParkingLot;
import com.smartpark.service.ParkingLotService;
import com.smartpark.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parkinglot")
public class ParkingLotController {
	
	@Autowired
	private ParkingLotService parkingLotService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@PostMapping
	public ResponseEntity<?> createParkingLot(@Valid @RequestBody ParkingLotDto parkingLotDto) {
		
		return ResponseEntity.ok(parkingLotService.createParkingLot(parkingLotDto));
	}
	
	@GetMapping("/{lotId}/vehicles")
	public ResponseEntity<?> getVehiclesInLot(@PathVariable String lotId) {
		
		return parkingLotService.getParkingLotById(lotId)
					.map(lot -> ResponseEntity.ok(vehicleService.getVehicleLot(lotId)))
					.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<ParkingLot>> getAllParkingLots() {
		
		return ResponseEntity.ok(parkingLotService.getAllParkingLots());
	}

}
