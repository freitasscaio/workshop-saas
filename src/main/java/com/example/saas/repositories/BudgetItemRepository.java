package com.example.saas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.BudgetItem;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {

}
