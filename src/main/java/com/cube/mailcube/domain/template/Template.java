package com.cube.mailcube.domain.template;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
