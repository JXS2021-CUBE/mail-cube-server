package com.cube.mailcube.domain.template;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
	Optional<Template> findById(Long id);
	List<Template> findAll();
}
