package com.askus.askus.domain.like.dto;

import com.askus.askus.domain.board.domain.Board;

import lombok.Getter;

@Getter
public class LikeResponse {
	private final long boardId;
	private final long likeCount;

	public LikeResponse(long boardId, long likeCount) {
		this.boardId = boardId;
		this.likeCount = likeCount;
	}

	public static LikeResponse ofEntity(Board board) {
		return new LikeResponse(board.getId(), board.getLikeCount());
	}
}
