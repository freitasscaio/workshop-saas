package com.example.saas.models;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budget_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "budget_id")
	private Budget budget;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private BigDecimal quantity;
	
	@Column(nullable = false)
	private BigDecimal unitPrice;
	
	@Column(nullable = false)
	private BigDecimal subTotal;

}
