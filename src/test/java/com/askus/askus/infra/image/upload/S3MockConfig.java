package com.askus.askus.infra.image.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import io.findify.s3mock.S3Mock;

@TestConfiguration
public class S3MockConfig {
	@Value("${cloud.aws.s3.bucket}")
	public String bucket;
	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public S3Mock s3Mock() {
		return new S3Mock.Builder()
			.withPort(8001)
			.withInMemoryBackend()
			.build();
	}

	@Bean
	public AmazonS3 amazonS3(S3Mock s3Mock) {
		s3Mock.start();
		AwsClientBuilder.EndpointConfiguration configuration =
			new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", region);
		AmazonS3 amazonS3Client = AmazonS3ClientBuilder
			.standard()
			.withPathStyleAccessEnabled(true)
			.withEndpointConfiguration(configuration)
			.withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
			.build();
		amazonS3Client.createBucket(bucket);
		return amazonS3Client;
	}
}
