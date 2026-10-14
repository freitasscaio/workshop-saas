package com.example.saas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	Optional<Vehicle> findByIdAndCustomer_Workshop_Id(Long id, Long workshopId);

	List<Vehicle> findByCustomer_IdAndCustomer_Workshop_Id(Long customerId, Long workshopId);

}
