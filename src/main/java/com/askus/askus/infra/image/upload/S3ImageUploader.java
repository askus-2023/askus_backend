package com.askus.askus.infra.image.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.service.ImageUploader;
import com.askus.askus.global.error.exception.FileException;
import com.askus.askus.global.properties.S3Properties;

import lombok.RequiredArgsConstructor;

/**
 * Implementation for Image Uploader using AWS S3 bucket
 * @Function - upload, delete
 * */
@Component
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {

	private final AmazonS3Client amazonS3Client;
	private final S3Properties s3Properties;

	@Override
	public String upload(Image image) {
		File file = convert(image)
			.orElseThrow(() -> new FileException("while converting file"));

		String filename = UUID.randomUUID().toString();
		String imageUrl = putS3(file, filename);
		deleteFile(file);
		return imageUrl;
	}

	private Optional<File> convert(Image image) {
		File file = new File("/tmp/" + image.getOriginalFilename());
		try {
			if (file.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					fos.write(image.getInputStream().readAllBytes());
				}
			}
		} catch (IOException e) {
			throw new FileException("while saving local file", e);
		}
		return Optional.of(file);
	}

	private String putS3(File file, String filename) {
		amazonS3Client.putObject(
			new PutObjectRequest(s3Properties.getBucket(), filename, file).withCannedAcl(
				CannedAccessControlList.PublicRead)
		);
		return amazonS3Client.getUrl(s3Properties.getBucket(), filename).toString();
	}

	private void deleteFile(File file) {
		try {
			file.delete();
		} catch (IllegalArgumentException e) {
			throw new FileException("while deleting local file", e);
		}
	}

	@Override
	public void delete(String filename) {
		amazonS3Client.deleteObject(s3Properties.getBucket(), filename);
	}
}
