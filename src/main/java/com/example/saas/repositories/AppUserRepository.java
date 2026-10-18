package com.example.saas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	
	Optional<AppUser> findByEmail(String email);

	List<AppUser> findAllByWorkshop_Id(Long workshopId);

}
