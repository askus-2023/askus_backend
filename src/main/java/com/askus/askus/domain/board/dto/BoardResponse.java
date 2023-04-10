package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.reply.dto.RepliesSearchResponse;
import com.askus.askus.domain.users.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class BoardResponse {
	@Getter
	@AllArgsConstructor
	public static class Post {
		private final Long boardId;
		private final String title;
		private final String nickname;
		private final String ingredients;
		private final Category category;
		private final String content;
		private final String tag;
		private final String thumbnailImageUrl;
		private final List<String> representativeImageUrls;

		public static BoardResponse.Post ofEntity(
			Board board,
			Users users,
			BoardImage thumbnailImage,
			List<BoardImage> representativeImages) {
			return new BoardResponse.Post(
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
		private final List<RepliesSearchResponse> replies;

		public Detail(
			String title,
			String author,
			String ingredients,
			String content,
			LocalDateTime createdAt,
			List<BoardImage> representativeImages,
			String tag,
			List<RepliesSearchResponse> replies) {
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
			List<RepliesSearchResponse> replies
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
