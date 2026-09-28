package com.example.saas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    @NotNull(message = "Cliente é obrigatório.")
	private Long customerId;
    
    @NotBlank(message = "Placa é obrigatória.")
    @Size(max = 10, message = "Placa deve ter no máximo 10 caracteres.")
    private String plate;
    
    @NotBlank(message = "Marca é obrigatória.")
    @Size(max = 50, message = "Marca deve ter no máximo 50 caracteres.")
    private String brand;
    
    @NotBlank(message = "Modelo é obrigatório.")
    @Size(max = 50, message = "Modelo deve ter no máximo 50 caracteres.")
    private String model;
    
    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900.")
    @Max(value = 2100, message = "Ano deve ser menor ou igual a 2100.")
    private Integer year;
    
}