package com.askus.askus.domain.like.dto;

import com.askus.askus.domain.board.domain.Board;

import lombok.Getter;

@Getter
public class LikeAddAndDeleteResponse {
	private final long boardId;
	private final long likeCount;

	public LikeAddAndDeleteResponse(long boardId, long likeCount) {
		this.boardId = boardId;
		this.likeCount = likeCount;
	}

	public static LikeAddAndDeleteResponse ofEntity(Board board) {
		return new LikeAddAndDeleteResponse(board.getId(), board.getLikeCount());
	}
}
