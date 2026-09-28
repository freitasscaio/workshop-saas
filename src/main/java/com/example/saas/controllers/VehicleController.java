package com.example.saas.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saas.dto.VehicleRequestDTO;
import com.example.saas.dto.VehicleResponseDTO;
import com.example.saas.models.Vehicle;
import com.example.saas.services.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
	
	private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public VehicleResponseDTO create(
            @Valid @RequestBody VehicleRequestDTO dto) {

        Vehicle vehicle = vehicleService.save(dto);

        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear()
        );
    }
    
}
