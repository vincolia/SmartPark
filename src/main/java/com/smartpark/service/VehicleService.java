package com.smartpark.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartpark.dto.ResponseDto;
import com.smartpark.dto.VehicleDto;
import com.smartpark.entity.ParkingLot;
import com.smartpark.entity.Vehicle;
import com.smartpark.repository.ParkingLotRepository;
import com.smartpark.repository.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VehicleService {
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private ParkingLotRepository parkingLotRepository;
	
	public Vehicle registerVehicle(VehicleDto vehicleDto) {
		logger.info("Starting VehicleService.vehicleCheckIn()");
		
		Vehicle vehicle = new Vehicle(vehicleDto);
		
		return vehicleRepository.save(vehicle);
	}
	
	public ResponseDto vehicleCheckIn(String licensePlate, String lotId) {
		logger.info("Starting VehicleService.vehicleCheckIn()");
		
		Optional<Vehicle> vehicleOpt = vehicleRepository.findById(licensePlate);
		Optional<ParkingLot> parkingLotOpt = parkingLotRepository.findById(lotId);
		
		if(vehicleOpt.isEmpty()) {
			return new ResponseDto("Vehicle not yet registered");
		}
		
		if(parkingLotOpt.isEmpty()) {
			return new ResponseDto("Parking lot does not exists");
		}
		
		ParkingLot parkingLot = parkingLotOpt.get();
		Vehicle vehicle = vehicleOpt.get();
		
		if(vehicleOpt.get().getParkingLot() != null) {
			return new ResponseDto("Vehicle is already parked");
		}
		
		if (parkingLot.getOccupiedSpaces() >= parkingLot.getCapacity()) {
			return new ResponseDto("Parking lot is full");
		}
		
		vehicle.setParkingLot(parkingLot);
		vehicle.setCheckIn(LocalDateTime.now());
		parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);
		
		vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);
		
		return new ResponseDto(String.format("%s Checkin in Successfully", vehicle.getLicensePlate()));
	}
	
	public ResponseDto vehicleCheckOut(String licensePlate) {
		logger.info("Starting VehicleService.vehicleCheckOut()");
		
		Optional<Vehicle> vehicleOpt = vehicleRepository.findById(licensePlate);
		
		if (vehicleOpt.isEmpty()) {
            return new ResponseDto("Vehicle not found");
        }
		
		Vehicle vehicle = vehicleOpt.get();

        if (vehicle.getParkingLot() == null) {
            return new ResponseDto("Vehicle is not yet parked");
        }
        
        ParkingLot parkingLot = vehicle.getParkingLot();
        
        long minutesParked = Duration.between(vehicle.getCheckIn(), LocalDateTime.now()).toMinutes();
        
        if(minutesParked == 0) {
        	minutesParked = 1;
        }
        
        BigDecimal cost = parkingLot.getCost().multiply(BigDecimal.valueOf(minutesParked));
        
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
        vehicle.setParkingLot(null);
        vehicle.setCheckIn(null);
        
        parkingLotRepository.save(parkingLot);
        vehicleRepository.save(vehicle);
		
		return new ResponseDto(String.format("Checked out successfully. Parking Cost: %s %s", parkingLot.getCurrency(), cost));
	}
	
	public List<VehicleDto> getVehicleLot(String lotId) {
		logger.info("Starting VehicleService.getVehicleLot()");
		
		List<VehicleDto> vehicleDtoList = vehicleRepository.findByParkingLot_LotId(lotId)
				.stream()
				.map(VehicleDto::new)
				.toList();
		
		return vehicleDtoList;
	}
	
	public VehicleDto getVehicleByLicensePlate(String licensePlate) {
		logger.info("Starting VehicleService.getVehicleByLicensePlate()");
		
		Vehicle vehicle = Optional.ofNullable(vehicleRepository.findById(licensePlate))
								.get()
								.orElse(new Vehicle());
		
		return new VehicleDto(vehicle);
	}
	
	public List<Vehicle> getAllVehicle() {
		logger.info("Starting VehicleService.getAllVehicle()");
		
		return vehicleRepository.findAll();
	}
	
	@Transactional
	public void removeVehiclesParkedLongerThanMinutes(long minutes) {
		logger.info("Starting VehicleService.removeVehiclesParkedLongerThanMinutes() {}", minutes);
		List<Vehicle> allParkedVehicle = vehicleRepository.findAll()
											.stream()
											.filter(vehicle -> vehicle.getParkingLot() != null && vehicle.getCheckIn() != null)
											.toList();
		
		List<String> removedVehicleList = new ArrayList<String>();
		
		LocalDateTime cutoff = LocalDateTime.now().minusMinutes(minutes);	
		
		allParkedVehicle.forEach(vehicle -> {
			if(vehicle.getCheckIn().isBefore(cutoff)) {
				ParkingLot parkingLot = vehicle.getParkingLot();
				
				if(parkingLot != null) {
					parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);
					vehicle.setParkingLot(null);
					vehicle.setCheckIn(null);
					
					parkingLotRepository.save(parkingLot);
					vehicleRepository.save(vehicle);
					
					removedVehicleList.add(vehicle.getLicensePlate());
				}
			}
		});
		
		logger.info("Vehicles removed : {}", removedVehicleList);
	}

}
