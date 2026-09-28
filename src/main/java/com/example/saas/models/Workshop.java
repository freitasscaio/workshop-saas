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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workshops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workshop implements Serializable {

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
	
	@OneToMany(mappedBy = "workshop")
	private List<Customer> customers = new ArrayList<>();
	
	@OneToMany(mappedBy = "workshop")
	private List<Budget> budgets = new ArrayList<>();
	
	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

}
