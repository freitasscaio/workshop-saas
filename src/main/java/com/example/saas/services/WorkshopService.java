package com.example.saas.services;

import org.springframework.stereotype.Service;

import com.example.saas.exceptions.ResourceNotFoundException;
import com.example.saas.models.Workshop;
import com.example.saas.repositories.WorkshopRepository;

@Service
public class WorkshopService {
	
	private final WorkshopRepository workshopRepository;
	
	public WorkshopService(WorkshopRepository workshopRepository) {
		this.workshopRepository = workshopRepository;
	}
	
	public Workshop findById(Long id) {
	    return workshopRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Oficina com ID " + id + " não encontrada."));
	}
	
	public Workshop save(Workshop workShop) {
		return workshopRepository.save(workShop);
	}

}
