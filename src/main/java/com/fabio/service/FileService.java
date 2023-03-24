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
import com.fabio.property.FileStorageProperties;

import lombok.NonNull;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class FileService {
	private Logger logger = Logger.getLogger(FileService.class.getName());

	private final Path fileStorageLocation;
	
	private final BlobServiceClient blobServiceClient;
	
	@Autowired
	public FileService(@NonNull FileStorageProperties fileStorageProperties, BlobServiceClient blobServiceClient) {
		logger.info("::"+fileStorageProperties.getTesteFabio());
		fileStorageLocation = Path.of(fileStorageProperties.getUploadDir());
		
		this.blobServiceClient = blobServiceClient;
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (IOException e) {
			logger.info("Could not create the directory where the upload files will be stored");
			e.printStackTrace();
		}
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
	
	public Boolean uploadAndDowloadFile(@NonNull MultipartFile file, String containerName) {
		boolean isSuccess = true;
		BlobContainerClient blobContainerClient = getBlobContainerClient(containerName);
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
