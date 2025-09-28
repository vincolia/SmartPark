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

import com.smartpark.dto.ResponseDto;
import com.smartpark.dto.VehicleDto;
import com.smartpark.entity.Vehicle;
import com.smartpark.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;

	@PostMapping
	public ResponseEntity<?> registerVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
		
		if(vehicleService.getVehicleByLicensePlate(vehicleDto.getLicensePlate()).getLicensePlate() != null) {
			return ResponseEntity.badRequest().body("License Plate already Registered");
		}
		
		return ResponseEntity.ok(vehicleService.registerVehicle(vehicleDto));
	}
	
	@PostMapping("/{licensePlate}/checkin/{lotId}")
	public ResponseEntity<ResponseDto> checkIn(@PathVariable String licensePlate, @PathVariable String lotId) {
		ResponseDto result = vehicleService.vehicleCheckIn(licensePlate, lotId);
		if(result.getMessage().toLowerCase().contains("success")) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	@PostMapping("/{licensePlate}/checkout")
	public ResponseEntity<ResponseDto> checkOut(@PathVariable String licensePlate) {
		ResponseDto result = vehicleService.vehicleCheckOut(licensePlate);
		
		if(result.getMessage().toLowerCase().contains("success")) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.badRequest().body(result);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Vehicle>> getAllVehicle() {
		
		return ResponseEntity.ok(vehicleService.getAllVehicle());
	}
}
