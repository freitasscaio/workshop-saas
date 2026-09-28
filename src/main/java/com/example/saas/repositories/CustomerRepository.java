package com.example.saas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
