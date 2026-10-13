package com.example.saas.services;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.saas.dto.WorkshopRegistrationRequestDTO;
import com.example.saas.dto.WorkshopRegistrationResponseDTO;
import com.example.saas.exceptions.BusinessRuleException;
import com.example.saas.models.AppUser;
import com.example.saas.models.Workshop;
import com.example.saas.repositories.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final WorkshopService workshopService;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(
            AppUserRepository appUserRepository,
            WorkshopService workshopService,
            PasswordEncoder passwordEncoder) {

        this.appUserRepository = appUserRepository;
        this.workshopService = workshopService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public WorkshopRegistrationResponseDTO registerWorkshopOwner(
            WorkshopRegistrationRequestDTO dto) {

        String ownerEmail = dto.getOwnerEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (appUserRepository.findByEmail(ownerEmail).isPresent()) {
            throw new BusinessRuleException(
                    "Já existe um usuário cadastrado com este e-mail."
            );
        }

        Workshop workshop = new Workshop();

        workshop.setName(dto.getWorkshopName().trim());
        workshop.setPhone(dto.getWorkshopPhone().trim());
        workshop.setEmail(normalizeOptionalEmail(dto.getWorkshopEmail()));

        Workshop savedWorkshop = workshopService.save(workshop);

        AppUser owner = new AppUser();

        owner.setWorkshop(savedWorkshop);
        owner.setName(dto.getOwnerName().trim());
        owner.setEmail(ownerEmail);
        owner.setPassword(passwordEncoder.encode(dto.getOwnerPassword()));
        owner.setRole("OWNER");

        AppUser savedOwner = appUserRepository.save(owner);

        return new WorkshopRegistrationResponseDTO(
                savedWorkshop.getId(),
                savedWorkshop.getName(),
                savedOwner.getId(),
                savedOwner.getName(),
                savedOwner.getEmail(),
                savedOwner.getRole(),
                savedOwner.getCreatedAt()
        );
    }

    private String normalizeOptionalEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }

        return email.trim().toLowerCase(Locale.ROOT);
    }
}