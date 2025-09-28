package com.smartpark.dto;

import java.math.BigDecimal;

import com.smartpark.entity.ParkingLot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ParkingLotDto {
	
	@NotBlank
    @Size(max = 50)
    private String lotId;
	
	@NotBlank
	private String location;
	
	@Positive
	private int capacity;
	
	@Positive
	private BigDecimal cost;
	
	private String currency;

	public ParkingLotDto(ParkingLot parkingLot) {
		super();
		this.lotId = parkingLot.getLotId();
		this.location = parkingLot.getLocation();
		this.capacity = parkingLot.getCapacity();
		this.cost = parkingLot.getCost();
		this.currency = parkingLot.getCurrency();
	}
	
	

}
