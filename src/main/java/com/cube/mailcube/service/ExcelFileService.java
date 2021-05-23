package com.cube.mailcube.service;

import com.cube.mailcube.domain.ApplicantDto;
import com.cube.mailcube.domain.excelfile.ExcelFile;
import com.cube.mailcube.domain.excelfile.ExcelFileRepository;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ExcelFileService {

	private final String EXCEL_FILE_PATH = "excel-file";

	private final ExcelFileRepository excelfileRepository;
	private final FileLoadService fileLoadService;

	@Transactional
	public String addExcelFile(File convertedFile, String multipartName) {
		Optional<ExcelFile> optionalExcelFile = excelfileRepository.findByName(multipartName);
		String s3_url = fileLoadService.upload(convertedFile, EXCEL_FILE_PATH, multipartName);

		if (optionalExcelFile.isPresent()) {
			return String.valueOf(optionalExcelFile.get().getId());
		}

		ExcelFile excelFile = excelfileRepository.save(
			ExcelFile.builder()
				.name(multipartName)
				.s3_url(s3_url)
				.datetime(new Timestamp(System.currentTimeMillis()))
				.build());
		return String.valueOf(excelFile.getId());
	}

	public Optional<ExcelFile> getExcelFileById(Long id) {
		return excelfileRepository.findById(id);
	}

	public List<ApplicantDto> getApplicants(String url) {
		List<ApplicantDto> applicants = null;

		try {
			URL urlAddress = new URL(url);
			URLConnection uc = urlAddress.openConnection();

			XSSFWorkbook workbook = new XSSFWorkbook(uc.getInputStream());

			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			applicants = new ArrayList<>();

			// applicant array initialize
			for (int i = 0; i < rows - 1; i++) {
				applicants.add(new ApplicantDto());
			}

			for (int rowindex = 1; rowindex < rows; rowindex++) {
				XSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int columnindex = 0; columnindex <= cells; columnindex++) {
						XSSFCell cell = row.getCell(columnindex);
						String value = "";
						if (cell == null) {
							continue;
						} else {
							switch (cell.getCellType()) {
								case XSSFCell.CELL_TYPE_STRING:
									value = cell.getStringCellValue() + "";
									break;

								case XSSFCell.CELL_TYPE_ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						if (columnindex == 0) {
							applicants.get(rowindex - 1).setName(value);
						} else if (columnindex == 1) {
							applicants.get(rowindex - 1).setEmail(value);
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return applicants;
	}

	public List<ExcelFile> getAllExcelFiles() {
		return excelfileRepository.findAll();
	}
}
