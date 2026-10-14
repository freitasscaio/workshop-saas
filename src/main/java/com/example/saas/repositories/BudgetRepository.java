package com.example.saas.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saas.models.Budget;
import com.example.saas.models.BudgetStatus;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

	Optional<Budget> findByIdAndWorkshop_Id(Long id, Long workshopId);

	List<Budget> findAllByWorkshop_Id(Long workshopId);

	List<Budget> findByExpirationDateBeforeAndStatusIn(LocalDate date, List<BudgetStatus> statuses);

}
