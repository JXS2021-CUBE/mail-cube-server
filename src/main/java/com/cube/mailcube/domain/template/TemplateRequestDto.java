package com.cube.mailcube.domain.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TemplateRequestDto {
	private final String title;
	private final String content;
}
