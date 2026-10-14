
package com.example.saas.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saas.dto.BudgetItemResponseDTO;
import com.example.saas.dto.BudgetListResponseDTO;
import com.example.saas.dto.BudgetRequestDTO;
import com.example.saas.dto.BudgetResponseDTO;
import com.example.saas.dto.CustomerResponseDTO;
import com.example.saas.dto.VehicleResponseDTO;
import com.example.saas.models.Budget;
import com.example.saas.services.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public BudgetResponseDTO create(
            @Valid @RequestBody BudgetRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        Budget budget = budgetService.save(dto, getWorkshopId(jwt));

        return toResponseDTO(budget);
    }

    @GetMapping("/{id}")
    public BudgetResponseDTO findById(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Budget budget = budgetService.findById(id, getWorkshopId(jwt));

        return toResponseDTO(budget);
    }

    @GetMapping
    public List<BudgetListResponseDTO> findAll(
            @AuthenticationPrincipal Jwt jwt) {

        List<Budget> budgets = budgetService.findAll(getWorkshopId(jwt));

        return budgets.stream()
                .map(budget -> new BudgetListResponseDTO(
                        budget.getId(),
                        budget.getCustomer().getName(),
                        budget.getVehicle().getBrand() + " "
                                + budget.getVehicle().getModel(),
                        budget.getStatus(),
                        budget.getIssueDate(),
                        budget.getTotalAmount()))
                .toList();
    }

    @PostMapping("/{id}/send")
    public BudgetResponseDTO send(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Budget budget = budgetService.send(id, getWorkshopId(jwt));

        return toResponseDTO(budget);
    }

    @PostMapping("/{id}/view")
    public BudgetResponseDTO markAsViewed(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Budget budget = budgetService.markAsViewed(
                id, getWorkshopId(jwt));

        return toResponseDTO(budget);
    }

    @PostMapping("/{id}/approve")
    public BudgetResponseDTO approve(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Budget budget = budgetService.approve(id, getWorkshopId(jwt));

        return toResponseDTO(budget);
    }

    @PostMapping("/{id}/reject")
    public BudgetResponseDTO reject(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        Budget budget = budgetService.reject(id, getWorkshopId(jwt));

        return toResponseDTO(budget);
    }

    private Long getWorkshopId(Jwt jwt) {
        Object claim = jwt.getClaim("workshopId");

        if (claim instanceof Number number) {
            return number.longValue();
        }

        if (claim instanceof String value) {
            return Long.parseLong(value);
        }

        throw new IllegalStateException(
                "O token não contém um workshopId válido.");
    }

    private BudgetResponseDTO toResponseDTO(Budget budget) {

        CustomerResponseDTO customerDTO =
                new CustomerResponseDTO(
                        budget.getCustomer().getId(),
                        budget.getCustomer().getName(),
                        budget.getCustomer().getPhone(),
                        budget.getCustomer().getEmail(),
                        budget.getCustomer().getCreatedAt());

        VehicleResponseDTO vehicleDTO =
                new VehicleResponseDTO(
                        budget.getVehicle().getId(),
                        budget.getVehicle().getPlate(),
                        budget.getVehicle().getBrand(),
                        budget.getVehicle().getModel(),
                        budget.getVehicle().getYear());

        List<BudgetItemResponseDTO> items =
                budget.getItems()
                        .stream()
                        .map(item -> new BudgetItemResponseDTO(
                                item.getId(),
                                item.getDescription(),
                                item.getType(),
                                item.getQuantity(),
                                item.getUnitPrice(),
                                item.getSubTotal()))
                        .toList();

        return new BudgetResponseDTO(
                budget.getId(),
                customerDTO,
                vehicleDTO,
                budget.getStatus(),
                budget.getIssueDate(),
                budget.getExpirationDate(),
                budget.getNotes(),
                budget.getTotalAmount(),
                items);
    }
}
