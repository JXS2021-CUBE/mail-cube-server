package com.cube.mailcube.controller;

import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class TemplateController {
	private final TemplateService templateService;

	@PostMapping("/templates")
	public ResponseEntity<Object> addTemplate(String title, String content) {
		if (title == null || content == null) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.INVALID_FIELD_ERROR));
		}
		return ResponseEntity.ok().body(templateService.addTemplate(title, content));
	}
}
