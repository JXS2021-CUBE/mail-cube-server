package com.cube.mailcube.service;

import com.cube.mailcube.domain.template.Template;
import com.cube.mailcube.domain.template.TemplateRepository;
import com.cube.mailcube.domain.template.TemplateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TemplateService {
	private final TemplateRepository templateRepository;

	@Transactional
	public String addTemplate(TemplateRequestDto templateRequestDto) {
		Template template = templateRepository.save(
				Template.builder()
						.title(templateRequestDto.getTitle())
						.content(templateRequestDto.getContent())
						.build());
		return String.valueOf(template.getId());
	}

	@Transactional
	public String updateTemplate(Template template) {
		templateRepository.save(template);
		return String.valueOf(template.getId());
	}

	@Transactional
	public void deleteTemplate(Long id) {
		templateRepository.deleteById(id);
	}

	public List<Template> getAllTemplates() {
		return templateRepository.findAll();
	}

	public Optional<Template> getTemplateById(Long id) {
		return templateRepository.findById(id);
	}
}
