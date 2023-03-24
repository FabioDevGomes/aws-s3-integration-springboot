package com.fabio.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

	private String uploadDir;
	private String testeFabio;
	
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public String getTesteFabio() {
		return testeFabio;
	}
	public void setTesteFabio(String testeFabio) {
		this.testeFabio = testeFabio;
	}
	
	
}
