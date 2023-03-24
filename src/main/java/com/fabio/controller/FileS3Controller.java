package com.fabio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fabio.aws.S3Util;

@Controller
public class FileS3Controller {
	
	@GetMapping("/")
	public String viewHomePage() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String handlerUploadForm(Model model, String description, @RequestParam("file") MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		
		System.out.println("Description "+ description);
		System.out.println("File name "+ fileName);
		
		String message = "";
		
		try {
			S3Util.uploadFile(fileName, multipartFile.getInputStream());
			message = "Bro... You file has been uploaded successfully!";
		} catch (Exception e) {
			message = "Erro uploading file: " + e.getMessage();
			e.printStackTrace();
		}
		
		model.addAttribute("message", message);
		
		return "message";
	}

}
