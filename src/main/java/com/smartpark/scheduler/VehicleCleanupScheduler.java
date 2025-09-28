package com.smartpark.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartpark.service.VehicleService;

@Component
public class VehicleCleanupScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleCleanupScheduler.class);
	
	@Autowired
	private VehicleService vehicleService;
	
	@Value("${scheduler.vehicle.cutoffMinutes}")
	private long cutoffMinutes;
	
	@Scheduled(fixedRateString = "${scheduler.vehicle.fixedRateMs}")
	public void removeVehicles() {
		logger.info("Scheduler: removing vehicles parked longer than {} minutes", cutoffMinutes);
		vehicleService.removeVehiclesParkedLongerThanMinutes(cutoffMinutes);
	}

}
