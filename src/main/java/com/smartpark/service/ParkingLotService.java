package com.smartpark.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartpark.dto.ParkingLotDto;
import com.smartpark.entity.ParkingLot;
import com.smartpark.repository.ParkingLotRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ParkingLotService {

	@Autowired
	private ParkingLotRepository parkingLotRepository;
	
	public ParkingLot createParkingLot(ParkingLotDto parkingLotDto) {	
		ParkingLot parkingLot = new ParkingLot(parkingLotDto);
		parkingLot.setOccupiedSpaces(0);
		
		return parkingLotRepository.save(parkingLot);
	}
	
	public Optional<ParkingLot> getParkingLotById(String lotId) {
		
		return parkingLotRepository.findById(lotId);
	}
	
	public List<ParkingLot> getAllParkingLots() {
		
		return parkingLotRepository.findAll();
	}
}
