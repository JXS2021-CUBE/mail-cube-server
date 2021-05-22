package com.cube.mailcube.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
	Optional<Applicant> findById(Long id);
}
