package com.askus.askus.domain.image.service;

import com.askus.askus.domain.image.domain.Image;

public interface ImageUploader {
	String upload(Image image);

	void delete(String filename);
}
