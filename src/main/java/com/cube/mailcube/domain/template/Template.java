package com.cube.mailcube.domain.template;

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
@Table(name = "template")
@NoArgsConstructor
public class Template {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;

	@Builder
	public Template(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
