package com.example.saas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 100, message = "Nome deve ter no máximo 100 caracteres.")
    private String name;
    
	@NotBlank(message = "Telefone é obrigatório")
	@Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres.")
    private String phone;
    
	@Email(message = "E-mail inválido")
	@Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres.")
    private String email;
    
}