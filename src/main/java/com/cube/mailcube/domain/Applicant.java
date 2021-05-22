package com.cube.mailcube.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "applicant")
@NoArgsConstructor
public class Applicant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String phone;
	@Column
	private String status;
	@ManyToOne
	@JoinColumn(name = "recruitment_id")
	private Recruitment recruitment;

	@Builder
	public Applicant(String name, String email, String phone, String status, Recruitment recruitment) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.recruitment = recruitment;
	}
}