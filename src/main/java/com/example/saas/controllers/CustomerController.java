
package com.example.saas.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saas.dto.CustomerRequestDTO;
import com.example.saas.dto.CustomerResponseDTO;
import com.example.saas.dto.VehicleResponseDTO;
import com.example.saas.models.Customer;
import com.example.saas.models.Vehicle;
import com.example.saas.services.CustomerService;
import com.example.saas.services.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final VehicleService vehicleService;

    public CustomerController(
            CustomerService customerService,
            VehicleService vehicleService) {
        this.customerService = customerService;
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public void create(
            @Valid @RequestBody CustomerRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        customerService.save(dto, getWorkshopId(jwt));
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO findById(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Customer customer = customerService.findById(id, getWorkshopId(jwt));

        return toResponseDTO(customer);
    }

    @GetMapping
    public List<CustomerResponseDTO> findAll(
            @AuthenticationPrincipal Jwt jwt) {

        return customerService.findAll(getWorkshopId(jwt))
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}/vehicles")
    public List<VehicleResponseDTO> findVehicles(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        // Verifica se o cliente pertence à oficina autenticada.
        customerService.findById(id, getWorkshopId(jwt));

        List<Vehicle> vehicles = vehicleService.findByCustomerId(id, getWorkshopId(jwt));

        return vehicles.stream()
                .map(vehicle -> new VehicleResponseDTO(
                        vehicle.getId(),
                        vehicle.getPlate(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getYear()))
                .toList();
    }

    private Long getWorkshopId(Jwt jwt) {
        return Long.valueOf(jwt.getClaimAsString("workshopId"));
    }

    private CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getCreatedAt());
    }
}
