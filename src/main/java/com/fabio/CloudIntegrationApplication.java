package com.fabio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fabio.property.AwsProperties;
import com.fabio.property.AzureProperties;

@SpringBootApplication
@EnableConfigurationProperties({AzureProperties.class})
public class CloudIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudIntegrationApplication.class, args);
	}

}
