package com.example.saas.controllers;

import com.example.saas.dto.WorkshopResponseDTO;
import com.example.saas.dto.WorkshopUpdateRequestDTO;
import com.example.saas.services.WorkshopService;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workshops")
public class WorkShopController {

    private final WorkshopService workshopService;

    public WorkShopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @GetMapping("/me")
    public ResponseEntity<WorkshopResponseDTO> findMyWorkshop(
            @AuthenticationPrincipal Jwt jwt) {

        Object workshopIdClaim = jwt.getClaim("workshopId");

        Long workshopId;

        if (workshopIdClaim instanceof Number number) {
            workshopId = number.longValue();
        } else {
            workshopId = Long.parseLong(workshopIdClaim.toString());
        }

        WorkshopResponseDTO workshop =
                workshopService.findResponseById(workshopId);

        return ResponseEntity.ok(workshop);
    }

    @PatchMapping("/me")
    public ResponseEntity<WorkshopResponseDTO> updateMyWorkshop(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody WorkshopUpdateRequestDTO dto){

        Object workshopIdClaim = jwt.getClaim("workshopId");

        Long workshopId;

        if(workshopIdClaim instanceof Number number){
            workshopId = number.longValue();
        } else {
            workshopId = Long.parseLong(workshopIdClaim.toString());
        }

        WorkshopResponseDTO updateWorkshop = workshopService.update(workshopId, dto);

        return ResponseEntity.ok(updateWorkshop);

    }
}