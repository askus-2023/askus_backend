package com.askus.askus.domain.board.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.FileException;
import com.askus.askus.global.util.SortConditions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardRequest {

	@Schema(description = "게시글 등록 request dto")
	@Getter
	public static class Post {
		@NotNull
		@Schema(description = "제목", example = "냉장고에 돼지고기와 김치찌개가 있다면???", required = true)
		private String title;
		@NotNull
		@Schema(description = "음식이름", example = "돼지고기 김치찌개", required = true)
		private String foodName;
		@NotNull
		@Schema(description = "카테고리", example = "KOREAN", required = true)
		private Category category;
		@NotNull
		@Schema(description = "재료", example = "김치, 돼지고기", required = true)
		private String ingredients;
		@NotNull
		@Schema(description = "내용", example = "돼지고기와 김치를 넣고 끓입니다.", required = true)
		private String content;
		@Schema(description = "태그", example = "한식,김치,돼지고기")
		private String tag;
		@Schema(description = "썸네일 이미지", example = "thumbnail.png")
		private Optional<Image> thumbnailImage = Optional.empty();
		@Schema(description = "일반 이미지", example = "image1.png, ...")
		private Optional<List<Image>> representativeImages = Optional.empty();
		private boolean thumbnailImageUpdate;
		private boolean representativeImageUpdate;

		public Post(
			String title,
			String foodName,
			Category category,
			String ingredients,
			String content,
			String tag,
			MultipartFile thumbnailImage,
			List<MultipartFile> representativeImages,
			boolean thumbnailImageUpdate,
			boolean representativeImageUpdate
		) {
			this.title = title;
			this.foodName = foodName;
			this.category = category;
			this.ingredients = ingredients;
			this.content = content;
			this.tag = tag;
			setThumbnailImage(thumbnailImage);
			setRepresentativeImages(representativeImages);
			this.thumbnailImageUpdate = thumbnailImageUpdate;
			this.representativeImageUpdate = representativeImageUpdate;
		}

		public void setThumbnailImage(MultipartFile thumbnailImage) {
			if (thumbnailImage == null || thumbnailImage.isEmpty()) {
				return;
			}
			this.thumbnailImage = Optional.of(new Image(
				getInputStream(thumbnailImage), getOriginalFileName(thumbnailImage)
			));
		}

		public void setRepresentativeImages(List<MultipartFile> representativeImages) {
			if (representativeImages == null || representativeImages.isEmpty()) {
				return;
			}
			long count = representativeImages.stream()
				.filter(file -> !file.isEmpty())
				.count();
			if (count != 0) {
				this.representativeImages = Optional.of(representativeImages.stream()
					.map(image -> new Image(getInputStream(image), getOriginalFileName(image)))
					.collect(Collectors.toList()));
			}
		}

		private InputStream getInputStream(MultipartFile image) {
			ByteArrayInputStream byteArrayInputStream;
			try {
				byte[] byteArray = image.getBytes();
				byteArrayInputStream = new ByteArrayInputStream(byteArray);
			} catch (IOException e) {
				throw new FileException("while converting file", e);
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
				this.foodName,
				this.category,
				this.ingredients,
				this.content,
				this.tag
			);
		}
	}

	@Getter
	public static class Summary {
		@Schema(description = "카테고리 검색조건", example = "KOREAN")
		private String tag;
		@Schema(description = "게시글 등록 시작일 검색조건", example = "2023-04-21")
		private Optional<LocalDateTime> dateLoe = Optional.empty();
		@Schema(description = "게시글 등록 마감일 검색조건", example = "2023-04-27")
		private Optional<LocalDateTime> dateGoe = Optional.empty();
		@NotNull
		@Schema(description = "게시글 정렬기준", example = "CREATED_AT_DESC", required = true)
		private SortConditions sortTarget;

		public Summary(
			String tag,
			String dateLoe,
			String dateGoe,
			String sortTarget) {
			this.tag = tag;
			if (!dateLoe.isBlank()) {
				this.dateLoe = Optional.of(LocalDate.parse(dateLoe, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
			}
			if (!dateGoe.isBlank()) {
				this.dateGoe = Optional.of(LocalDate.parse(dateGoe, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
			}
			this.sortTarget = SortConditions.valueOf(sortTarget);
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Delete {
		@NotNull
		@Schema(description = "삭제할 board ID", example = "1, ... ")
		private List<Long> boardIds;
	}
}
