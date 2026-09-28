package com.example.saas.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.saas.exceptions.BusinessRuleException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Budget implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	
	@ManyToOne
	@JoinColumn(name = "workshop_id", nullable = false)
	private Workshop workshop;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;
	
	@Setter(AccessLevel.NONE)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BudgetStatus status = BudgetStatus.DRAFT;
	
	@Column(nullable = false)
	private LocalDate issueDate;
	
	private LocalDate expirationDate;
	
	private String notes;
	
	@Column(nullable = false)
	private BigDecimal totalAmount;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "budget",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<BudgetItem> items = new ArrayList<>();
	
	@PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        issueDate = LocalDate.now();
        expirationDate = issueDate.plusDays(7);

        if (status == null) {
            status = BudgetStatus.DRAFT;
        }

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public void send() {

        if (status != BudgetStatus.DRAFT) {
            throw new BusinessRuleException(
                    "Somente um orçamento em DRAFT pode ser enviado."
            );
        }

        status = BudgetStatus.SENT;
    }
    
    public void markAsViewed() {

        if (status != BudgetStatus.SENT) {
            throw new BusinessRuleException(
                    "Somente um orçamento enviado pode ser visualizado."
            );
        }

        status = BudgetStatus.VIEWED;
    }
    
    public void approve() {

        if (status != BudgetStatus.SENT &&
            status != BudgetStatus.VIEWED) {

            throw new BusinessRuleException(
                    "O orçamento não pode ser aprovado neste estado."
            );
        }

        status = BudgetStatus.APPROVED;
    }
    
    public void reject() {

        if (status != BudgetStatus.SENT &&
            status != BudgetStatus.VIEWED) {

            throw new BusinessRuleException(
                    "O orçamento não pode ser recusado neste estado."
            );
        }

        status = BudgetStatus.REJECTED;
    }
    
    public void cancel() {

        if (status != BudgetStatus.DRAFT &&
            status != BudgetStatus.SENT &&
            status != BudgetStatus.VIEWED) {

            throw new BusinessRuleException(
                    "O orçamento não pode ser cancelado neste estado."
            );
        }

        status = BudgetStatus.CANCELED;
    }
    
    public void expire() {

        if (status != BudgetStatus.DRAFT &&
            status != BudgetStatus.SENT &&
            status != BudgetStatus.VIEWED) {

            throw new BusinessRuleException(
                    "O orçamento não pode ser expirado neste estado."
            );
        }

        status = BudgetStatus.EXPIRED;
    }

}
