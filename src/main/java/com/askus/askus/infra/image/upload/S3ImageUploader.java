package com.askus.askus.infra.image.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.askus.askus.domain.image.service.ImageUploader;
import com.askus.askus.domain.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(Image image) throws IOException {
        File file = convert(image)
            .orElseThrow(() -> new IllegalArgumentException("파일 생성 과정에서 문제 발생"));

        String filename = UUID.randomUUID().toString();
        String imageUrl = putS3(file, filename);
        deleteFile(file);
        return imageUrl;
    }

    private Optional<File> convert(Image image) throws IOException {
        File file = new File(image.getOriginalFilename());
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(image.getInputStream().readAllBytes());
            }
        }
        return Optional.of(file);
    }

    private String putS3(File file, String filename) {
        amazonS3Client.putObject(
            new PutObjectRequest(bucket, filename, file).withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, filename).toString();
    }

    private void deleteFile(File file) {
        try {
            file.delete();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("로컬 파일 삭제 실패", e);
        }
    }

    @Override
    public void delete(String filename) {
        amazonS3Client.deleteObject(bucket, filename);
    }
}
