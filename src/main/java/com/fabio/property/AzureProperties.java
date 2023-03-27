package com.fabio.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class AzureProperties {

	private String azureContainerName;
	private String testeFabio;
	private String uploadDir;

	
	public String getTesteFabio() {
		return testeFabio;
	}
	
	public void setTesteFabio(String testeFabio) {
		this.testeFabio = testeFabio;
	}
	
	public String getAzureContainerName() {
		return azureContainerName;
	}
	
	public void setAzureContainerName(String azureContainerName) {
		this.azureContainerName = azureContainerName;
	}
	
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	
}
