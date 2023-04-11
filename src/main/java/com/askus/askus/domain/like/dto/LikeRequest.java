package com.askus.askus.domain.like.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.like.domain.Like;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

@Getter
public class LikeRequest {
	private long boardId;

	public LikeRequest(long boardId) {
		this.boardId = boardId;
	}

	public Like toEntity(Users users, Board board) {
		return new Like(users, board);
	}
}
