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
@Table(name = "recruitment")
@NoArgsConstructor
public class Recruitment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String department;
	@Column(nullable = false)
	private String recruitSection;

	@Builder
	public Recruitment(String title, String department, String recruitSection) {
		this.title = title;
		this.department = department;
		this.recruitSection = recruitSection;
	}
}