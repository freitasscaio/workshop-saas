
package com.example.saas.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.saas.dto.BudgetItemRequestDTO;
import com.example.saas.dto.BudgetRequestDTO;
import com.example.saas.exceptions.BusinessRuleException;
import com.example.saas.exceptions.ResourceNotFoundException;
import com.example.saas.models.Budget;
import com.example.saas.models.BudgetItem;
import com.example.saas.models.BudgetStatus;
import com.example.saas.models.Customer;
import com.example.saas.models.Vehicle;
import com.example.saas.models.Workshop;
import com.example.saas.repositories.BudgetRepository;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final WorkshopService workshopService;

    public BudgetService(
            BudgetRepository budgetRepository,
            CustomerService customerService,
            VehicleService vehicleService,
            WorkshopService workshopService) {

        this.budgetRepository = budgetRepository;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.workshopService = workshopService;
    }

    public Budget save(BudgetRequestDTO dto, Long workshopId) {

        Customer customer = customerService.findById(
                dto.getCustomerId(), workshopId);

        Vehicle vehicle = vehicleService.findById(
                dto.getVehicleId(), workshopId);

        if (!vehicle.getCustomer().getId().equals(customer.getId())) {
            throw new BusinessRuleException(
                    "O veículo não pertence ao cliente informado.");
        }

        Workshop workshop = workshopService.findById(workshopId);

        Budget budget = new Budget();

        budget.setWorkshop(workshop);
        budget.setCustomer(customer);
        budget.setVehicle(vehicle);

        BigDecimal total = BigDecimal.ZERO;

        for (BudgetItemRequestDTO itemDTO : dto.getItems()) {
            BigDecimal subtotal = itemDTO.getQuantity()
                    .multiply(itemDTO.getUnitPrice());

            BudgetItem item = new BudgetItem();

            item.setBudget(budget);
            item.setDescription(itemDTO.getDescription());
            item.setType(itemDTO.getType());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setSubTotal(subtotal);

            budget.getItems().add(item);

            total = total.add(subtotal);
        }

        budget.setTotalAmount(total);
        budget.setNotes(dto.getNotes());

        return budgetRepository.save(budget);
    }

    public Budget findById(Long id, Long workshopId) {
        return budgetRepository.findByIdAndWorkshop_Id(id, workshopId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Orçamento com ID " + id + " não encontrado."));
    }

    public List<Budget> findAll(Long workshopId) {
        return budgetRepository.findAllByWorkshop_Id(workshopId);
    }

    public Budget send(Long id, Long workshopId) {
        Budget budget = findById(id, workshopId);

        budget.send();

        return budgetRepository.save(budget);
    }

    public Budget markAsViewed(Long id, Long workshopId) {
        Budget budget = findById(id, workshopId);

        budget.markAsViewed();

        return budgetRepository.save(budget);
    }

    public Budget approve(Long id, Long workshopId) {
        Budget budget = findById(id, workshopId);

        budget.approve();

        return budgetRepository.save(budget);
    }

    public Budget reject(Long id, Long workshopId) {
        Budget budget = findById(id, workshopId);

        budget.reject();

        return budgetRepository.save(budget);
    }

    public void expireExpiredBudgets() {

        List<Budget> budgets =
                budgetRepository.findByExpirationDateBeforeAndStatusIn(
                        LocalDate.now(),
                        List.of(BudgetStatus.SENT, BudgetStatus.VIEWED));

        for (Budget budget : budgets) {
            budget.expire();
        }

        budgetRepository.saveAll(budgets);
    }
}
