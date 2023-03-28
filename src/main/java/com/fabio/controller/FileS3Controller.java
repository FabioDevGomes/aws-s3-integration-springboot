package com.fabio.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fabio.aws.S3Util;

@Controller
public class FileS3Controller {
	
	@GetMapping("/fileAws")
	public String viewHomePage() {
		return "awsUpload";
	}
	
	@PostMapping(value = "/fileAws/upload", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public ResponseEntity<?> handlerUploadForm(String description, @RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		
		System.out.println("::::: Description "+ description);
		System.out.println("::::: File name "+ fileName);
		
		ByteArrayResource resource = null;
		
		try {
			S3Util.uploadFile(fileName, file.getInputStream());
			resource = new ByteArrayResource(S3Util.downloadFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.OK).contentLength(resource.contentLength()).body(resource);
	}

}
