package com.example.saas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.saas.dto.CustomerRequestDTO;
import com.example.saas.exceptions.ResourceNotFoundException;
import com.example.saas.models.Customer;
import com.example.saas.models.Workshop;
import com.example.saas.repositories.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final WorkshopService workshopService;

    public CustomerService(
            CustomerRepository customerRepository,
            WorkshopService workshopService) {

        this.customerRepository = customerRepository;
        this.workshopService = workshopService;
    }

    public Customer save(CustomerRequestDTO dto) {

        Workshop workshop = workshopService.findById(1L);

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setWorkshop(workshop);

        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente com id " + id + " não encontrado"));
    }
    
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
}