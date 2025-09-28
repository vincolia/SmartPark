package com.smartpark.dto;

import java.util.Optional;

import com.smartpark.entity.Vehicle;
import com.smartpark.enums.VehicleTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class VehicleDto {
	
	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "Invalid License Plate Format can only contains letters, numbers, and dashes")
	private String licensePlate;
	
    @NotNull
	private VehicleTypeEnum type;
	
	@NotBlank
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Owner name Format can contain only letters, and spaces")
	private String ownerName;
	
	private String parkingLotId;

	public VehicleDto(Vehicle vehicle) {
		super();
		this.licensePlate = vehicle.getLicensePlate();
		this.type = vehicle.getType();
		this.ownerName = vehicle.getOwnerName();
		this.parkingLotId = vehicle.getParkingLot() != null ? vehicle.getParkingLot().getLotId() : null;
		
	}
}
