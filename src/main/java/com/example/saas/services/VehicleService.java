package com.example.saas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.saas.dto.VehicleRequestDTO;
import com.example.saas.exceptions.ResourceNotFoundException;
import com.example.saas.models.Customer;
import com.example.saas.models.Vehicle;
import com.example.saas.repositories.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CustomerService customerService) {

        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
    }

    public Vehicle save(VehicleRequestDTO dto) {

        Customer customer = customerService.findById(dto.getCustomerId());

        Vehicle vehicle = new Vehicle();

        vehicle.setCustomer(customer);
        vehicle.setPlate(dto.getPlate());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());

        return vehicleRepository.save(vehicle);
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle com id " + id + " não encontrado."));
    }
    
    public List<Vehicle> findByCustomerId(Long customerId) {

        customerService.findById(customerId);

        return vehicleRepository.findByCustomerId(customerId);
    }
    
}