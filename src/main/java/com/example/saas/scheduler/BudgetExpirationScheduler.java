package com.example.saas.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.saas.services.BudgetService;

@Component
public class BudgetExpirationScheduler {
	
	private final BudgetService budgetService;

	public BudgetExpirationScheduler(BudgetService budgetService) {
		this.budgetService = budgetService;
	}
	
	@Scheduled(cron = "0 0 * * * *")
	public void expireBudgets() {
		budgetService.expireExpiredBudgets();
	}
	
}
