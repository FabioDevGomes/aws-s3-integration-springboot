package com.fabio.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.fabio.property.AzureProperties;

import lombok.NonNull;

@Service
public class AzureFileService {
	private Logger logger = Logger.getLogger(AzureFileService.class.getName());

	private Path fileStorageLocation;
	private BlobServiceClient blobServiceClient;
	private String azureContainerName;
	
	@Autowired
	public AzureFileService(@NonNull AzureProperties fileStorageProperties, BlobServiceClient blobServiceClient) {
		init(fileStorageProperties, blobServiceClient);
		
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (IOException e) {
			logger.info("Could not create the directory where the upload files will be stored");
			e.printStackTrace();
		}
	}

	private void init(AzureProperties fileStorageProperties, BlobServiceClient blobServiceClient) {
		this.blobServiceClient = blobServiceClient;
		azureContainerName = fileStorageProperties.getAzureContainerName();
		fileStorageLocation = Path.of(fileStorageProperties.getUploadDir());
	}
	
	public Path getFileStorageLocation() {
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (IOException e) {
			logger.info("Could not create the directory where the upload files will be stored");
			e.printStackTrace();
		}
		return fileStorageLocation;
	}
	
	public Boolean uploadAndDowloadFile(@NonNull MultipartFile file) {
		boolean isSuccess = true;
		BlobContainerClient blobContainerClient = getBlobContainerClient(azureContainerName);
		String fileName = file.getOriginalFilename();
		BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(fileName).getBlockBlobClient();
		try {
			//delete file if already exists in that container
			if (blockBlobClient.exists()) {
				blockBlobClient.delete();
			}
			//upload file to azure blob storage
			blockBlobClient.upload(new BufferedInputStream(file.getInputStream()), file.getSize(), true);
			String tempFilePath = "./uploads/" + fileName;
			Files.deleteIfExists(Paths.get(tempFilePath));
			
			//download file from azure blob storage to a file
			blockBlobClient.downloadToFile(new File(tempFilePath).getPath());
		} catch (IOException e) {
			isSuccess = false;	
			logger.info("Error while processing file");
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	private @NonNull BlobContainerClient getBlobContainerClient(@NonNull String containerName) {
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		if (!blobContainerClient.exists()) {
			blobContainerClient.create();
		}
		return blobContainerClient;
	}
	
}
