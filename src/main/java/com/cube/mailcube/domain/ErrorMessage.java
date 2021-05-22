package com.cube.mailcube.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {
	private final int status;
	private final String message;
}
