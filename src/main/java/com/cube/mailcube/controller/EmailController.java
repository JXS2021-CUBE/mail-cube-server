package com.cube.mailcube.controller;

import com.cube.mailcube.domain.EmailRequestDto;
import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/excels/{id}/email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequestDto emailRequestDto, @PathVariable("id") Long id) {
        if (emailRequestDto.getTitle() == null || emailRequestDto.getContent() == null
                || emailRequestDto.getSenderEmail() == null || emailRequestDto.getSenderName() == null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                            ErrorCase.INVALID_FIELD_ERROR));
        }

        emailService.sendEmail(emailRequestDto, id);

        return ResponseEntity.ok().build();

    }
}
