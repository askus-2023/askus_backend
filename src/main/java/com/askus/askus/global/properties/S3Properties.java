package com.askus.askus.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@ConfigurationProperties(prefix = "cloud.aws.s3")
@ConstructorBinding
@Getter
public class S3Properties {
	private final String bucket;

	public S3Properties(String bucket) {
		this.bucket = bucket;
	}
}
