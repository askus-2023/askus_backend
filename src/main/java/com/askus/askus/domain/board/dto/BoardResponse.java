package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.users.domain.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Response DTO for domain Board
 * */
public class BoardResponse {
	@Getter
	@AllArgsConstructor
	public static class Post {
		@Schema(description = "게시글 ID", example = "1")
		private final long boardId;
		@Schema(description = "제목", example = "냉장고에 돼지고기와 김치찌개가 있다면???")
		private final String title;
		@Schema(description = "음식이름", example = "돼지고기 김치찌개")
		private final String foodName;
		@Schema(description = "작성자", example = "쿠킹마마")
		private final String author;
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
			Users users
		) {
			return new BoardResponse.Post(
				board.getId(),
				board.getTitle(),
				board.getFoodName(),
				users.getNickname(),
				board.getIngredients(),
				board.getCategory(),
				board.getContent(),
				board.getTag(),
				board.getThumbnailImage() == null ? null : board.getThumbnailImage().getUrl(),
				board.getRepresentativeImages().stream()
					.map(representativeImage -> representativeImage.getUrl())
					.collect(Collectors.toList())
			);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Summary {
		@Schema(description = "게시글 ID", example = "1")
		private long id;
		private String foodsName;
		private Category category;
		private boolean myLike;
		private String authorProfileImageUrl;
		private String title;
		@Schema(description = "작성자", example = "쿠킹마마")
		private String author;
		@Schema(description = "게시글 등록일", example = "2023.04.18")
		private LocalDateTime createdAt;
		@Schema(description = "썸네일 이미지 주소", example = "http://thumbnail/image/url")
		private String thumbnailImageUrl;
		@Schema(description = "게시글 좋아요 수", example = "101")
		private long likeCount;
		@Schema(description = "게시글 댓글 수", example = "36")
		private long replyCount;
	}

	@Data
	public static class Detail {
		@Schema(description = "제목", example = "돼지고기 김치찌개")
		private final String title;
		@Schema(description = "음식명", example = "돼지고기 김치찌개")
		private final String foodName;
		@Schema(description = "작성자", example = "쿠킹마마")
		private final String author;
		@Schema(description = "현재 로그인된 사용자가 해당 게시글의 작성자인지 여부", example = "true/false")
		private final boolean myBoard;
		@Schema(description = "재료", example = "김치, 돼지고기")
		private final String ingredients;
		@Schema(description = "카테고리", example = "KOREAN")
		private final Category category;
		@Schema(description = "내용", example = "김치와 돼지고기를 넣고 끓입니다.")
		private final String content;
		@Schema(description = "게시글 작성일", example = "2023.04.17")
		private final LocalDateTime createdAt;
		@Schema(description = "썸네일 이미지 주소", example = "http://thumbnail/image/url")
		private final String thumbnailImageUrl;
		@Schema(description = "태그", example = "한식,김치,돼지고기")
		private final String tag;
		@Schema(description = "게시글 좋아요 개수", example = "36")
		private final long likeCount;
		@Schema(description = "현재 로그인된 사용자가 해당 게시글에 좋아요를 눌렀는지 여부", example = "true/false")
		private final boolean myLike;
		@Schema(description = "일반 이미지 주소 리스트", example = "[http://representative/image/url1, http://representative/image/url2]")
		private List<String> representativeImageUrls;

		public Detail(
			String title,
			String foodName,
			String author,
			boolean myBoard,
			String ingredients,
			Category category,
			String content,
			LocalDateTime createdAt,
			String thumbnailImageUrl,
			String tag,
			long likeCount,
			boolean myLike
		) {
			this.title = title;
			this.foodName = foodName;
			this.author = author;
			this.myBoard = myBoard;
			this.ingredients = ingredients;
			this.category = category;
			this.content = content;
			this.createdAt = createdAt;
			this.thumbnailImageUrl = thumbnailImageUrl;
			this.tag = tag;
			this.likeCount = likeCount;
			this.myLike = myLike;
		}
	}
}
