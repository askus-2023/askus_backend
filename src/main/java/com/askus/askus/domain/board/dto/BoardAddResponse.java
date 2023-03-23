package com.askus.askus.domain.board.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

@Getter
public class BoardAddResponse {
	private final Long id;
	private final String title;
	private final String nickname;
	private final String ingredients;
	private final Category category;
	private final String content;
	private final String tag;
	private final String thumbnailImageUrl;
	private final String representativeImageUrl;

	public BoardAddResponse(
		Long id,
		String title,
		String nickname,
		String ingredients,
		Category category,
		String content,
		String tag,
		String thumbnailImageUrl,
		String representativeImageUrl) {
		this.id = id;
		this.title = title;
		this.nickname = nickname;
		this.ingredients = ingredients;
		this.category = category;
		this.content = content;
		this.tag = tag;
		this.thumbnailImageUrl = thumbnailImageUrl;
		this.representativeImageUrl = representativeImageUrl;
	}

	public static BoardAddResponse ofEntity(
		Board board,
		Users users,
		BoardImage thumbnailImage,
		BoardImage representativeImage) {
		return new BoardAddResponse(
			board.getId(),
			board.getTitle(),
			users.getNickname(),
			board.getIngredients(),
			board.getCategory(),
			board.getContent(),
			board.getTag(),
			thumbnailImage.getUrl(),
			representativeImage.getUrl()
		);
	}
}
