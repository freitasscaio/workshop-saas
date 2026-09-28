package com.example.saas.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Budget;
import com.example.saas.models.BudgetStatus;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

	List<Budget> findByExpirationDateBeforeAndStatusIn(LocalDate date, List<BudgetStatus> statuses);

}
