package com.example.saas.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkshopResponseDTO {

	private Long id;
	private String name;
	private String phone;
	private String email;
	private LocalDateTime createdAt;
	
}