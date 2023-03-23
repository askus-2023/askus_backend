package com.askus.askus.infra.image.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.askus.askus.domain.image.service.ImageUploader;
import com.askus.askus.domain.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(Image image) {
        return null;
    }
}
