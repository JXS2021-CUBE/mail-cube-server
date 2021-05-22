package com.cube.mailcube.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "employee")
@NoArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String department;

	@Builder
	public Employee(String email, String name, String department) {
		this.email = email;
		this.name = name;
		this.department = department;
	}
}