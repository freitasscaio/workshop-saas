package com.example.saas.controllers;

import java.util.List;

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

    public CustomerController(CustomerService customerService, VehicleService vehicleService) {
        this.customerService = customerService;
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public void create(@Valid @RequestBody CustomerRequestDTO dto) {
    	customerService.save(dto);
    }
    
    @GetMapping("/{id}")
    public CustomerResponseDTO findById(@PathVariable Long id) {

        Customer customer = customerService.findById(id);

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
    
    @GetMapping
    public List<CustomerResponseDTO> findAll() {

        List<Customer> customers = customerService.findAll();

        return customers.stream()
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getCreatedAt()
                ))
                .toList();
    }
    
    @GetMapping("/{id}/vehicles")
    public List<VehicleResponseDTO> findVehicles(@PathVariable Long id) {

        List<Vehicle> vehicles = vehicleService.findByCustomerId(id);

        return vehicles.stream()
                .map(vehicle -> new VehicleResponseDTO(
                        vehicle.getId(),
                        vehicle.getPlate(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getYear()
                ))
                .toList();
    }

    
    
}