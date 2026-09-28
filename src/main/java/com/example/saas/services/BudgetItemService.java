package com.example.saas.services;

import org.springframework.stereotype.Service;

import com.example.saas.exceptions.ResourceNotFoundException;
import com.example.saas.models.BudgetItem;
import com.example.saas.repositories.BudgetItemRepository;

@Service
public class BudgetItemService {

    private final BudgetItemRepository budgetItemRepository;

    public BudgetItemService(BudgetItemRepository budgetItemRepository) {
        this.budgetItemRepository = budgetItemRepository;
    }
    
    public BudgetItem findById(Long id) {
        return budgetItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do orçamento com ID " + id + " não encontrado."));
    }

    public BudgetItem save(BudgetItem budgetItem) {
        return budgetItemRepository.save(budgetItem);
    }
}