package com.cube.mailcube.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findById(Long id);
}
