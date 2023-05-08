package com.askus.askus.domain.like.dto;

import com.askus.askus.domain.board.domain.Board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Response DTO for domain Like
 * */
@Getter
public class LikeResponse {
	@Schema(description = "board ID", example = "1")
	private final long boardId;
	@Schema(description = "좋아요 수", example = "36")
	private final long likeCount;

	public LikeResponse(long boardId, long likeCount) {
		this.boardId = boardId;
		this.likeCount = likeCount;
	}

	public static LikeResponse ofEntity(Board board) {
		return new LikeResponse(board.getId(), board.getLikeCount());
	}
}
