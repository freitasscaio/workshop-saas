package com.example.saas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.saas.models.BudgetStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponseDTO {

    private Long id;

    private CustomerResponseDTO customer;

    private VehicleResponseDTO vehicle;

    private BudgetStatus status;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    private String notes;

    private BigDecimal totalAmount;

    private List<BudgetItemResponseDTO> items;
    
}