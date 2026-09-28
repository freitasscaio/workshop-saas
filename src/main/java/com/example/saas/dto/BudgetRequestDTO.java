package com.example.saas.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class BudgetRequestDTO {

	@NotNull(message = "Cliente é obrigatório.")
    private Long customerId;
    
	@NotNull(message = "Veículo é obrigatório.")
    private Long vehicleId;
    
	@Size(max = 500, message = "Observação deve ter no máximo 500 caracteres.")
    private String notes;
	
    @NotEmpty(message = "O orçamento deve possuir pelo menos um item.")
    @Valid
    private List<BudgetItemRequestDTO> items;
    
}