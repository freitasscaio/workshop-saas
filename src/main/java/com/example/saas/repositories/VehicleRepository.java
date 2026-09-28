package com.example.saas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	List<Vehicle> findByCustomerId(Long customerId);

}
