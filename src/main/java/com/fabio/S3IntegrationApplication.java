package com.fabio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fabio.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class S3IntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3IntegrationApplication.class, args);
	}

}
