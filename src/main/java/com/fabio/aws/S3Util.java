package com.fabio.aws;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.utils.IoUtils;

public class S3Util {
	public static final String BUCKET = "fabio-test-first-bucket";
	static String keyFile = "";
	static S3Client client;
	
	public static void uploadFile(String fileName, InputStream inputStream) throws S3Exception, AwsServiceException, SdkClientException, IOException{
		setProperties();
		keyFile = fileName;
		client = S3Client.builder().build();
		
		PutObjectRequest request = PutObjectRequest.builder()
				.bucket(BUCKET)
				.key(fileName)
				.acl("public-read")
				.contentType("image/jpg")			
				.build();
		
		client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
	}
	
	private static void setProperties() {
		System.setProperty("aws.region", "sa-east-1");
		System.setProperty("aws.accessKeyId", "AKIAW3UJ24CPCXIY4YOR");
		System.setProperty("aws.secretAccessKey", "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");
	}
	
	public static byte[] downloadFile() throws IOException {
		GetObjectRequest request = GetObjectRequest.builder()
				.bucket(BUCKET)
				.key(keyFile)
				.build();
		
		ResponseInputStream<GetObjectResponse> response = client.getObject(request);

		return IoUtils.toByteArray(response);
		
//		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(keyFile));
//		byte[] buffer = new byte[4096];
//		int bytesRead = -1;
//		
//		while ((bytesRead = response.read(buffer)) != -1) {
//			outputStream.write(buffer, 0, bytesRead);
//		}
		
//		response.close();
//		outputStream.close();
	}
	
}
