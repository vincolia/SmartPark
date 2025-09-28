package com.smartpark.entity;

import java.math.BigDecimal;

import com.smartpark.dto.ParkingLotDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PARKING_LOT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLot {
	
	@Id
	@Column(length = 50, nullable = false, unique = true)
	@Size(max = 50)
	private String lotId;
	
	@NotBlank
	private String location;
	
	@Positive
	private int capacity;
	
	@PositiveOrZero
	private int occupiedSpaces;
	
	@Positive
	private BigDecimal cost;
	
	@NotBlank
	private String currency;

	public ParkingLot(ParkingLotDto parkingLotDto) {
		super();
		this.lotId = parkingLotDto.getLotId();
		this.location = parkingLotDto.getLocation();
		this.capacity = parkingLotDto.getCapacity();
		this.cost = parkingLotDto.getCost();
		this.currency = parkingLotDto.getCurrency();
	}
	
	
	
}
