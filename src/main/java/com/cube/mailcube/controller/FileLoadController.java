package com.cube.mailcube.controller;

import com.cube.mailcube.domain.ErrorCase;
import com.cube.mailcube.domain.ErrorMessage;
import com.cube.mailcube.domain.excelfile.ExcelFile;
import com.cube.mailcube.service.ExcelFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class FileLoadController {
    private final ExcelFileService excelFileService;

    @PostMapping("/excels")
    public ResponseEntity<Object> addExcelFile(
            @RequestParam("file") MultipartFile multipartFile) {
        String multipartName = multipartFile.getOriginalFilename();
        if (multipartName == null) {
            return ResponseEntity.badRequest().body(
                    new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ErrorCase.EMPTY_FILE_ERROR));
        }
        return ResponseEntity
                .created(URI.create("/excels/" + excelFileService.addExcelFile(multipartFile, multipartName)))
                .build();
    }

    @GetMapping("/excels/{id}")
    public ResponseEntity<Object> getExcelFile(@PathVariable("id") Long id) {
        Optional<ExcelFile> excelFile = excelFileService.getExcelFilebyId(id);
        return excelFile.<ResponseEntity<Object>>map(
            file -> ResponseEntity.ok().body(excelFileService.getApplicants(file.getBlob_url())))
            .orElseGet(() -> ResponseEntity.badRequest().body(
                new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                    ErrorCase.FAIL_FILE_CONVERT_ERROR)));
    }

    @GetMapping("/excels")
    public ResponseEntity<Object> getAllExcelFile() {
        return ResponseEntity.ok().body(excelFileService.getAllExcelFiles());
    }
}
