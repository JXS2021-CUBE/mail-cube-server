package com.cube.mailcube.controller;

import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.domain.template.TemplateRequestDto;
import com.cube.mailcube.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class TemplateController {
	private final TemplateService templateService;

	@PostMapping("/templates")
	public ResponseEntity<Object> addTemplate(@RequestBody TemplateRequestDto templateRequestDto) {
		if (templateRequestDto.getTitle() == null || templateRequestDto.getContent() == null) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.INVALID_FIELD_ERROR));
		}
		return ResponseEntity.ok().body(templateService.addTemplate(templateRequestDto));
	}
}
