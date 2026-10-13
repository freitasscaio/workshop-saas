package com.example.saas.controllers;

import com.example.saas.dto.WorkshopRegistrationRequestDTO;
import com.example.saas.dto.WorkshopRegistrationResponseDTO;
import com.example.saas.services.AppUserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class WorkshopRegistrationController {

    private final AppUserService appUserService;

    public WorkshopRegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/workshop")
    public ResponseEntity<WorkshopRegistrationResponseDTO> registerWorkshopOwner(
            @Valid @RequestBody WorkshopRegistrationRequestDTO dto) {

        WorkshopRegistrationResponseDTO response =
                appUserService.registerWorkshopOwner(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}