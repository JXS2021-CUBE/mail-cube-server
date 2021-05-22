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
@Table(name = "sending")
@NoArgsConstructor
public class Sending {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String sender;
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	@ManyToOne
	@JoinColumn(name = "template_id")
	private Template template;
	@ManyToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;

	@Builder
	public Sending(String sender, Employee employee, Template template, Applicant applicant) {
		this.sender = sender;
		this.employee = employee;
		this.template = template;
		this.applicant = applicant;
	}
}