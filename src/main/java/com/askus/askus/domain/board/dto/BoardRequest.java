package com.askus.askus.domain.board.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;
import com.askus.askus.global.util.SortConditions;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class BoardRequest {
	@Getter
	public static class Post {
		private String title;
		private Category category;
		private String ingredients;
		private String content;
		private String tag;
		private Image thumbnailImage;
		private List<Image> representativeImages = new ArrayList<>();

		public Post(
			String title,
			String category,
			String ingredients,
			String content,
			String tag,
			MultipartFile thumbnailImage,
			List<MultipartFile> representativeImages
		) {
			this.title = title;
			this.category = Category.valueOf(category);
			this.ingredients = ingredients;
			this.content = content;
			this.tag = tag;
			setThumbnailImage(thumbnailImage);
			setRepresentativeImages(representativeImages);
		}

		public void setThumbnailImage(MultipartFile thumbnailImage) {
			this.thumbnailImage = new Image(
				ImageType.THUMBNAIL, getInputStream(thumbnailImage), getOriginalFileName(thumbnailImage)
			);
		}

		public void setRepresentativeImages(List<MultipartFile> representativeImages) {
			this.representativeImages = representativeImages.stream()
				.map(image -> new Image(ImageType.REPRESENTATIVE, getInputStream(image), getOriginalFileName(image)))
				.collect(Collectors.toList());
		}

		private InputStream getInputStream(MultipartFile image) {
			ByteArrayInputStream byteArrayInputStream;
			try {
				byte[] byteArray = image.getBytes();
				byteArrayInputStream = new ByteArrayInputStream(byteArray);
			} catch (IOException e) {
				throw new KookleRuntimeException("이미지 파일 변환 실패", e);
			}
			return byteArrayInputStream;
		}

		private String getOriginalFileName(MultipartFile image) {
			return image.getOriginalFilename();
		}

		public Board toEntity(Users users) {
			return new Board(
				users,
				this.title,
				this.category,
				this.ingredients,
				this.content,
				this.tag
			);
		}
	}

	@Getter
	public static class Summary {
		/* 검색조건 */
		private String tag;
		private LocalDateTime dateLoe;
		private LocalDateTime dateGoe;
		/* 정렬기준 */
		private SortConditions sortTarget;

		public Summary(
			String tag,
			LocalDateTime dateLoe,
			LocalDateTime dateGoe,
			String sortTarget) {
			this.tag = tag;
			this.dateLoe = dateLoe;
			this.dateGoe = dateGoe;
			this.sortTarget = SortConditions.valueOf(sortTarget);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Delete {
		private List<Long> boardIds;
	}
}
