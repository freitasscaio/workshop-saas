package com.example.saas.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppUserResponseDTO {

    private Long id;
    private Long workshopId;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;

}