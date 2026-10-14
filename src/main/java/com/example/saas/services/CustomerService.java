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

    public Customer save(CustomerRequestDTO dto, Long workshopId) {

        Workshop workshop = workshopService.findById(workshopId);

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setWorkshop(workshop);

        return customerRepository.save(customer);
    }

    public Customer findById(Long id, Long workshopId) {
        return customerRepository.findByIdAndWorkshop_Id(id, workshopId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente com id " + id + " não encontrado"));
    }
    
    public List<Customer> findAll(Long workshopId) {
        return customerRepository.findAllByWorkshop_Id(workshopId);
    }
    
}