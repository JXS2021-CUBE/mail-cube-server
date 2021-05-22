package com.cube.mailcube.service;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class FileLoadService {

	private final CloudBlobContainer cloudBlobContainer;

	public URI fileUpload(MultipartFile multipartFile, String multipartName) {
		URI uri;

		try {
			CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(multipartName);
			blob.upload(multipartFile.getInputStream(), -1);
			uri = blob.getUri();
		} catch (IOException | URISyntaxException | StorageException e) {
			return null;
		}
		return uri;
	}
}
