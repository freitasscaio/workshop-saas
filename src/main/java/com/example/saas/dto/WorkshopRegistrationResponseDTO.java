package com.example.saas.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkshopRegistrationResponseDTO {

    private Long workshopId;
    private String workshopName;

    private Long ownerId;
    private String ownerName;
    private String ownerEmail;
    private String role;
    private LocalDateTime ownerCreatedAt;
}