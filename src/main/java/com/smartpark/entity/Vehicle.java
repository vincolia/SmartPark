package com.smartpark.entity;

import java.time.LocalDateTime;

import com.smartpark.dto.VehicleDto;
import com.smartpark.enums.VehicleTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VEHICLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
	
	@Id
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "Invalid License Plate Format can only contains letters, numbers, and dashes")
	private String licensePlate;
	
	@Enumerated(EnumType.STRING)
    @NotNull
	private VehicleTypeEnum type;
	
	@NotBlank
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Owner name Format can contain only letters, and spaces")
	private String ownerName;
	
	@ManyToOne
	@JoinColumn(name = "parking_lot_id")
	private ParkingLot parkingLot;
	
	private LocalDateTime checkIn;

	public Vehicle(VehicleDto vehicleDto) {
		super();
		this.licensePlate = vehicleDto.getLicensePlate();
		this.type = vehicleDto.getType();
		this.ownerName = vehicleDto.getOwnerName();
	}
	
	
}
