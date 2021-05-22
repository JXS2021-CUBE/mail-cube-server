package com.cube.mailcube.domain.excelfile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelFileRepository extends JpaRepository<ExcelFile, Long> {
	Optional<ExcelFile> findById(Long id);
	Optional<ExcelFile> findByName(String name);
}
