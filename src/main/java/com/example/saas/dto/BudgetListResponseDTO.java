package com.example.saas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.saas.models.BudgetStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetListResponseDTO {
	
	private Long id;
	
	private String customerName;
	
	private String vehicle;
	
	private BudgetStatus status;
	
	private LocalDate issueDate;
	
	private BigDecimal totalAmount;

}
