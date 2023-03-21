package com.fabio.aws;

import java.io.IOException;
import java.io.InputStream;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3Util {
	public static final String BUCKET = "fabio-test-first-bucket";
	
	public static void uploadFile(String fileName, InputStream inputStream) throws S3Exception, AwsServiceException, SdkClientException, IOException{
		System.setProperty("aws.region", "sa-east-1");
		System.setProperty("aws.accessKeyId", "AKIAW3UJ24CPCXIY4YOR");
		System.setProperty("aws.secretAccessKey", "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");
		S3Client client = S3Client.builder().build();
		
		PutObjectRequest request = PutObjectRequest.builder()
				.bucket(BUCKET)
				.key(fileName)
				.acl("public-read")
				.contentType("image/jpg")			
				.build();
		
		client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
	}
	
}
