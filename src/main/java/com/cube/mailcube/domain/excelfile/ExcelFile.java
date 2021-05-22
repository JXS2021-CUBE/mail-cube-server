package com.cube.mailcube.domain.excelfile;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Entity
@Table(name = "Excelfile")
@NoArgsConstructor
public class ExcelFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String blob_url;
	@Column(nullable = false)
	private String name;
	@Column(updatable = false)
	@CreatedDate
	private Timestamp datetime;

	@Builder
	public ExcelFile(String blob_url, Timestamp datetime, String name) {
		this.blob_url = blob_url;
		this.datetime = datetime;
		this.name = name;
	}
}
