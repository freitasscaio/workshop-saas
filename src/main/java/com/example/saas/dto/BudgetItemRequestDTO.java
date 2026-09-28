package com.example.saas.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
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
public class BudgetItemRequestDTO {

	@NotBlank(message = "Descrição é obrigatória.")
	@Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres.")
    private String description;
    
	@NotNull(message = "Tipo do item é obrigatório.")
    private String type;
    
	@NotNull(message = "Quantidade é obrigatória.")
	@DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero.")
    private BigDecimal quantity;
    
	@NotNull(message = "Preço unitário é obrigatório.")
	@DecimalMin(value = "0.00", message = "Preço unitário não pode ser negativo.")
    private BigDecimal unitPrice;
    
    
}