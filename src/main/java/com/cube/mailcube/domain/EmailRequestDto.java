package com.cube.mailcube.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmailRequestDto {
    private final String title;
    private final String content;
    private final String senderName;
    private final String senderEmail;
}
