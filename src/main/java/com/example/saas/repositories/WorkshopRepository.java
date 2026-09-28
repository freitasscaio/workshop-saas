package com.example.saas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Workshop;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

}
