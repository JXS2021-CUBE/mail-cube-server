package com.cube.mailcube.controller;

import com.cube.mailcube.domain.ApplicantDto;
import com.cube.mailcube.domain.EmailRequestDto;
import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.domain.excelfile.ExcelFile;
import com.cube.mailcube.service.EmailService;
import com.cube.mailcube.service.ExcelFileService;
import java.util.List;
import java.util.Optional;
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
	private final ExcelFileService excelFileService;
	private final EmailService emailService;

	@PostMapping("/excels/{id}/email")
	public ResponseEntity<Object> sendEmail(@RequestBody EmailRequestDto emailRequestDto,
		@PathVariable("id") Long id) {
		if (emailRequestDto.getTitle() == null || emailRequestDto.getContent() == null
			|| emailRequestDto.getSenderEmail() == null
			|| emailRequestDto.getSenderName() == null) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.INVALID_FIELD_ERROR));
		}

		Optional<ExcelFile> excelFile = excelFileService.getExcelFileById(id);
		if (!excelFile.isPresent()) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.NO_SUCH_FILE));
		}

		List<ApplicantDto> applicants = excelFileService.getApplicants(excelFile.get().getS3_url());
		if (applicants == null) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.FAIL_FILE_PARSING_ERROR));
		}

		emailService.sendEmail(emailRequestDto, applicants);

		return ResponseEntity.noContent().build();
	}
}
