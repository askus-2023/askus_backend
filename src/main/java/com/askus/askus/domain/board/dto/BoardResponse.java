package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.reply.dto.ReplyResponse;
import com.askus.askus.domain.users.domain.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class BoardResponse {
	@Getter
	@AllArgsConstructor
	public static class Post {
		@Schema(description = "게시글 ID", example = "1")
		private final Long boardId;
		@Schema(description = "제목", example = "돼지고기 김치찌개")
		private final String title;
		@Schema(description = "작성자", example = "쿠킹마마")
		private final String nickname;
		@Schema(description = "재료", example = "김치, 돼지고기")
		private final String ingredients;
		@Schema(description = "카테고리", example = "KOREAN")
		private final Category category;
		@Schema(description = "내용", example = "김치와 돼지고기를 넣고 끓입니다.")
		private final String content;
		@Schema(description = "태그", example = "한식,김치,돼지고기")
		private final String tag;
		@Schema(description = "썸네일 이미지 주소", example = "http://thumbnail/image/url")
		private final String thumbnailImageUrl;
		@Schema(description = "일반 이미지 주소 리스트", example = "[http://representative/image/url1, http://representative/image/url2]")
		private final List<String> representativeImageUrls;

		public static BoardResponse.Post ofEntity(
			Board board,
			Users users,
			Optional<BoardImage> thumbnailImage,
			Optional<List<BoardImage>> representativeImages) {
			return new BoardResponse.Post(
				board.getId(),
				board.getTitle(),
				users.getNickname(),
				board.getIngredients(),
				board.getCategory(),
				board.getContent(),
				board.getTag(),
				thumbnailImage.isPresent() ? thumbnailImage.get().getUrl() : null,
				representativeImages.isPresent() ?
					representativeImages.get().stream().map(BoardImage::getUrl).collect(Collectors.toList()) : null
			);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Patch {
		private final Long boardId;
		private final String title;
		private final String nickname;
		private final String ingredients;
		private final Category category;
		private final String content;
		private final String tag;
		private final String thumbnailImageUrl;
		private final List<String> representativeImageUrls;

		public static BoardResponse.Patch ofEntity(
			Board board,
			Users users,
			BoardImage thumbnailImage,
			List<BoardImage> representativeImages) {
			return new BoardResponse.Patch(
				board.getId(),
				board.getTitle(),
				users.getNickname(),
				board.getIngredients(),
				board.getCategory(),
				board.getContent(),
				board.getTag(),
				thumbnailImage.getUrl(),
				representativeImages.stream()
					.map(BoardImage::getUrl)
					.collect(Collectors.toList())
			);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Summary {
		private long id;
		private String author;
		private LocalDateTime createdAt;
		private String url;
		private long likeCount;
		private long replyCount;
	}

	@Getter
	public static class Detail {
		private final String title;
		private final String author;
		private final String ingredients;
		private final String content;
		private final LocalDateTime createdAt;
		private final List<String> urls;
		private final String tag;
		private final List<ReplyResponse.Summary> replies;

		public Detail(
			String title,
			String author,
			String ingredients,
			String content,
			LocalDateTime createdAt,
			List<BoardImage> representativeImages,
			String tag,
			List<ReplyResponse.Summary> replies) {
			this.title = title;
			this.author = author;
			this.ingredients = ingredients;
			this.content = content;
			this.createdAt = createdAt;
			this.urls = representativeImages.stream()
				.map(BoardImage::getUrl)
				.collect(Collectors.toList());
			this.tag = tag;
			this.replies = replies;
		}

		public static Detail ofEntity(
			Users users,
			Board board,
			List<BoardImage> representativeImages,
			List<ReplyResponse.Summary> replies
		) {
			return new Detail(
				board.getTitle(),
				users.getNickname(),
				board.getIngredients(),
				board.getContent(),
				board.getCreatedAt(),
				representativeImages,
				board.getTag(),
				replies
			);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Delete {
		private final List<Long> boardIds;

		public static Delete ofEntity(List<Long> boardIds) {
			return new Delete(boardIds);
		}
	}
}
