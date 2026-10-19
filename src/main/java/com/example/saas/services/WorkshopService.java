package com.example.saas.services;

import com.example.saas.dto.WorkshopResponseDTO;
import com.example.saas.dto.WorkshopUpdateRequestDTO;
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

	public WorkshopResponseDTO findResponseById(Long id){
		Workshop workshop = findById(id);

		return new WorkshopResponseDTO(
				workshop.getId(),
				workshop.getName(),
				workshop.getPhone(),
				workshop.getEmail(),
				workshop.getCreatedAt()
		);
	}

	public WorkshopResponseDTO update(
			Long id,
			WorkshopUpdateRequestDTO dto) {

		Workshop workshop = findById(id);

		if (dto.getName() != null) {
			workshop.setName(dto.getName());
		}

		if (dto.getPhone() != null) {
			workshop.setPhone(dto.getPhone());
		}

		if (dto.getEmail() != null) {
			workshop.setEmail(dto.getEmail());
		}

		Workshop updatedWorkshop = workshopRepository.save(workshop);

		return new WorkshopResponseDTO(
				updatedWorkshop.getId(),
				updatedWorkshop.getName(),
				updatedWorkshop.getPhone(),
				updatedWorkshop.getEmail(),
				updatedWorkshop.getCreatedAt()
		);
	}

}
