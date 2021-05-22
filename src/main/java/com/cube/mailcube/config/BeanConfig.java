package com.cube.mailcube.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class BeanConfig {

	@Autowired
	private Environment environment;

	@Bean
	public CloudBlobClient cloudBlobClient() throws URISyntaxException, StorageException, InvalidKeyException {
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(
			Objects.requireNonNull(environment.getProperty("azure.storage.ConnectionString")));
		return storageAccount.createCloudBlobClient();
	}

	@Bean
	public CloudBlobContainer testBlobContainer() throws URISyntaxException, StorageException, InvalidKeyException {
		return cloudBlobClient().getContainerReference(
			Objects.requireNonNull(environment.getProperty("azure.storage.container.name")));
	}
}