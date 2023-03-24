package com.askus.askus.domain.image.service;

import java.io.IOException;

import com.askus.askus.domain.image.domain.Image;

public interface ImageUploader {
    String upload(Image image) throws IOException;
    void delete(String filename);
}
