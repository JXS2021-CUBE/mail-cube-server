package com.cube.mailcube.service;

import com.cube.mailcube.domain.excelfile.ExcelFile;
import com.cube.mailcube.domain.excelfile.ExcelFileRepository;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor
public class ExcelFileService {

	private final ExcelFileRepository excelfileRepository;
	private final FileLoadService fileLoadService;

	@Transactional
	public String addExcelFile(MultipartFile multipartFile, String multipartName) {
		Optional<ExcelFile> optionalExcelFile = excelfileRepository.findByName(multipartName);
		if (optionalExcelFile.isPresent()) {
			fileLoadService.fileUpload(multipartFile, multipartName);
			return String.valueOf(optionalExcelFile.get().getId());
		}

		URI blob_url = fileLoadService.fileUpload(multipartFile, multipartName);

		ExcelFile excelFile = excelfileRepository.save(
			ExcelFile.builder()
				.name(multipartName)
				.blob_url(String.valueOf(blob_url))
				.datetime(new Timestamp(System.currentTimeMillis()))
				.build());
		return String.valueOf(excelFile.getId());
	}

	public List<ExcelFile> getAllExcelFiles() {
		return excelfileRepository.findAll();
	}
}
