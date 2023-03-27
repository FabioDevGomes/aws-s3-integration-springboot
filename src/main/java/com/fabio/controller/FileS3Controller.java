package com.fabio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fabio.aws.S3Util;

@Controller
public class FileS3Controller {
	
	@GetMapping("/fileAws")
	public String viewHomePage() {
		return "awsUpload";
	}
	
	@PostMapping("/fileAws/upload")
	public String handlerUploadForm(Model model, String description, @RequestParam("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		
		System.out.println("Description "+ description);
		System.out.println("File name "+ fileName);
		
		String message = "";
		
		try {
			S3Util.uploadFile(fileName, file.getInputStream());
			message = "Bro... You file has been uploaded successfully!";
		} catch (Exception e) {
			message = "Erro uploading file: " + e.getMessage();
			e.printStackTrace();
		}
		
		model.addAttribute("message", message);
		
		return "message";
	}

}
