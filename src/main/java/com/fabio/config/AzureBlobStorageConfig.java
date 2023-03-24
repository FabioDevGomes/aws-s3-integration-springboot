package com.fabio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;

@Configuration
public class AzureBlobStorageConfig {

	@Value("${azure.storage.account-name}")
	private String accoutName;
	
	@Value("${azure.storage.account-key}")
	private String accountKey;

	@Value("${azure.storage.blob-endpoint}")
	private String blobEndopint;
	
	@Bean
	public BlobServiceClient getBlobServiceClient() {
		return new BlobServiceClientBuilder().endpoint(blobEndopint)
				.credential(new StorageSharedKeyCredential(accoutName, accountKey))
				.buildClient();
	}
}
