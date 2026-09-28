package com.example.saas.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetItemResponseDTO {

    private Long id;
    
    private String description;
    
    private String type;
    
    private BigDecimal quantity;
    
    private BigDecimal unitPrice;
    
    private BigDecimal subtotal;
    
}