package com.example.saas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    
}