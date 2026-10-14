
package com.example.saas.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
            @Valid @RequestBody VehicleRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        Vehicle vehicle = vehicleService.save(
                dto, getWorkshopId(jwt));

        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear());
    }

    private Long getWorkshopId(Jwt jwt) {
        Object claim = jwt.getClaim("workshopId");

        if (claim instanceof Number number) {
            return number.longValue();
        }

        if (claim instanceof String value) {
            return Long.parseLong(value);
        }

        throw new IllegalStateException(
                "O token não contém um workshopId válido.");
    }
}
