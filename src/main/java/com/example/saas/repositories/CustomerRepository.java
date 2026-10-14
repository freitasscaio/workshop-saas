package com.example.saas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByIdAndWorkshop_Id(Long id, Long workshopId);

    List<Customer> findAllByWorkshop_Id(Long workshopId);

}
