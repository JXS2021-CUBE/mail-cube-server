package com.cube.mailcube.service;

import com.cube.mailcube.domain.ApplicantDto;
import com.cube.mailcube.domain.excelfile.ExcelFile;
import com.cube.mailcube.domain.excelfile.ExcelFileRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public Optional<ExcelFile> getExcelFilebyId(Long id) {
        return excelfileRepository.findById(id);
    }

    public List<ApplicantDto> getApplicants(String url) {

        List<ApplicantDto> applicants = null;

        try {

            URL urlAddress = new URL(url);// cloud의 파일 불러와서 경로 추가
            URLConnection uc = urlAddress.openConnection();

            XSSFWorkbook workbook = new XSSFWorkbook(uc.getInputStream());

            int rowindex = 0;
            int columnindex = 0;
            //시트 수 (첫번째에만 존재하므로 0을 준다)
            XSSFSheet sheet = workbook.getSheetAt(0);
            //행의 수
            int rows = sheet.getPhysicalNumberOfRows();

            applicants = new ArrayList<>();

            // applicant array 초기화
            for (int i = 0; i < rows-1; i++) {
                applicants.add(new ApplicantDto());
            }

            for (rowindex = 1; rowindex < rows; rowindex++) {
//                System.out.println("rowindex" + rowindex);
                //행을읽는다
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) {
                    //셀의 수
                    int cells = row.getPhysicalNumberOfCells();
                    for (columnindex = 0; columnindex <= cells; columnindex++) {
//                        System.out.println("columnindex" + columnindex);
                        //셀값을 읽는다
                        XSSFCell cell = row.getCell(columnindex);
                        String value = "";
                        //셀이 빈값일경우를 위한 널체크
                        if (cell == null) {
                            continue;
                        } else {
                            //타입별로 내용 읽기
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
//                            System.out.println(value);
                            applicants.get(rowindex-1).setName(value);
                        } else if (columnindex == 1) {
//                            System.out.println(value);
                            applicants.get(rowindex-1).setEmail(value);
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applicants;
    }

    public List<ExcelFile> getAllExcelFiles() {
        return excelfileRepository.findAll();
    }

}
