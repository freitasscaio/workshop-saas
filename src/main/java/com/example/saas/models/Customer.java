package com.example.saas.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phone;
	
	private String email;
	
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "workshop_id")
	private Workshop workshop;
	
	@OneToMany(mappedBy = "customer")
	private List<Vehicle> vehicles = new ArrayList<>();
	
	@OneToMany(mappedBy = "customer")
	private List<Budget> budgets = new ArrayList<>();
	
	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

}
