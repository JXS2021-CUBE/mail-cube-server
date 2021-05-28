package com.cube.mailcube.controller;

import com.cube.mailcube.domain.ApplicantDto;
import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.domain.excelfile.ExcelFile;
import com.cube.mailcube.service.ExcelFileService;
import com.cube.mailcube.service.FileLoadService;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class FileLoadController {
	private final FileLoadService fileLoadService;
	private final ExcelFileService excelFileService;

	@PostMapping("/excels")
	public ResponseEntity<Object> addExcelFile(
		@RequestParam("file") MultipartFile multipartFile) {
		String multipartName = multipartFile.getOriginalFilename();

		if (multipartFile.isEmpty() || multipartName == null) {
			return ResponseEntity.badRequest().body(
				new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.EMPTY_FILE_ERROR));
		}

		File convertedFile = fileLoadService.convert(multipartFile);
		if (convertedFile == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ErrorCase.FAIL_FILE_CONVERT_ERROR));
		}
		if (!fileLoadService.isValidExtension(convertedFile)) {
			return ResponseEntity.badRequest().body(
				new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.INVALID_FILE_TYPE_ERROR));
		}

		return ResponseEntity
			.created(URI.create(
				"/excels/" + excelFileService.addExcelFile(convertedFile, multipartName))).build();
	}

	@GetMapping("/excels/{id}")
	public ResponseEntity<Object> getExcelFile(@PathVariable("id") Long id) {
		Optional<ExcelFile> excelFile = excelFileService.getExcelFileById(id);
		if (!excelFile.isPresent()) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.NO_SUCH_FILE));
		}

		List<ApplicantDto> result = excelFileService.getApplicants(excelFile.get().getS3_url());
		if (result == null) {
			return ResponseEntity.badRequest()
				.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
					ErrorCase.FAIL_FILE_PARSING_ERROR));
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/excels")
	public ResponseEntity<Object> getAllExcelFile() {
		return ResponseEntity.ok().body(excelFileService.getAllExcelFiles());
	}
}
