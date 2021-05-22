package com.cube.mailcube.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendingRepository extends JpaRepository<Sending, Long> {
	Optional<Sending> findById(Long id);
}
