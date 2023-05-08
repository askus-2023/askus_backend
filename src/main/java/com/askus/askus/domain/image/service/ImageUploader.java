package com.askus.askus.domain.image.service;

import com.askus.askus.domain.image.domain.Image;

public interface ImageUploader {

	/**
	 * upload files by given request(Image)
	 *
	 * @param image - Image
	 * @Return - uploaded image url
	 */
	String upload(Image image);

	/**
	 * delete files by given request(filename)
	 *
	 * @param filename - filename
	 */
	void delete(String filename);
}
