package com.cube.mailcube.service;

import com.cube.mailcube.domain.Template;
import com.cube.mailcube.domain.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TemplateService {
	private final TemplateRepository templateRepository;

	@Transactional
	public String addTemplate(String title, String content) {
		Template template = templateRepository.save(
			Template.builder()
			.name(title)
			.content(content)
			.build());
		return String.valueOf(template.getId());
	}
}
