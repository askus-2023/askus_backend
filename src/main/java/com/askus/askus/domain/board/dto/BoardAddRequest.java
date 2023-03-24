package com.askus.askus.domain.board.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

@Getter
public class BoardAddRequest {
	private String title;
	private Category category;
	private String ingredients;
	private String content;
	private String tag;
	private Image thumbnailImage;
	private Image representativeImage;

	public BoardAddRequest(
		String title,
		String category,
		String ingredients,
		String content,
		String tag,
		Image thumbnailImage,
		Image representativeImage) {
		this.title = title;
		this.category = Category.valueOf(category);
		this.ingredients = ingredients;
		this.content = content;
		this.tag = tag;
		this.thumbnailImage = thumbnailImage;
		this.representativeImage = representativeImage;
	}

	public void setThumbnailImage(MultipartFile thumbnailImage) {
		InputStream inputStream = getInputStream(thumbnailImage);
		String originalFileName = getOriginalFileName(thumbnailImage);
		this.thumbnailImage = new Image(ImageType.THUMBNAIL, inputStream, originalFileName);
	}
	public void setRepresentativeImage(MultipartFile representativeImage) {
		InputStream inputStream = getInputStream(representativeImage);
		String originalFileName = getOriginalFileName(representativeImage);
		this.representativeImage = new Image(ImageType.REPRESENTATIVE, inputStream, originalFileName);
	}

	private InputStream getInputStream(MultipartFile file) {
		ByteArrayInputStream byteArrayInputStream;
		try {
			byte[] byteArray = file.getBytes();
			byteArrayInputStream = new ByteArrayInputStream(byteArray);
		} catch (IOException e) {
			throw new RuntimeException(e); //TODO 글로벌 예외 처리
		}
		return byteArrayInputStream;
	}

	private String getOriginalFileName(MultipartFile file) {
		return file.getOriginalFilename();
	}

	public Board toEntity(Users users) {
		return new Board(users, this.title, this.category, this.ingredients, this.content, this.tag);
	}
}
