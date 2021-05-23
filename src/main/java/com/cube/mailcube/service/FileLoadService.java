package com.cube.mailcube.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cube.mailcube.domain.ErrorCase;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-aws.yml")
public class FileLoadService {
	private final static List<String> WHITE_LISTS = Collections.singletonList(".xlsx");
	private final static String TEMP_FILE_PATH = "src/main/resources/";
	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;

	public File convert(MultipartFile file) {
		File convertFile = new File(TEMP_FILE_PATH + file.getOriginalFilename());
		try {
			if (convertFile.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(convertFile)) {
					fos.write(file.getBytes());
				}
				return convertFile;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public String upload(File uploadFile, String dirName, String id) {
		String fileName = dirName + "/" + id;
		String uploadImageUrl = putS3(uploadFile, fileName);
		deleteLocalFile(uploadFile);
		return uploadImageUrl;
	}

	public boolean isValidExtension(File uploadFile) {
		String fileName = uploadFile.getName();
		String extension = "." + fileName.substring(fileName.toLowerCase().lastIndexOf(".") + 1);
		if (!WHITE_LISTS.contains(extension)) {
			deleteLocalFile(uploadFile);
			return false;
		}
		return true;
	}

	public void delete(String dirName, String id) {
		deleteFromS3(dirName + "/" + id);
	}

	private void deleteFromS3(String key) {
		amazonS3Client.deleteObject(bucket, key);
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
			new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
				CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void deleteLocalFile(File uploadFile) {
		if (!uploadFile.delete()) {
			System.err.println(ErrorCase.FAIL_FILE_DELETE_ERROR);
		}
	}
}
