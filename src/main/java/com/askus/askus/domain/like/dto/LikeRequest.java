package com.askus.askus.domain.like.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.like.domain.Like;
import com.askus.askus.domain.users.domain.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequest {
	@Schema(description = "board ID", example = "1")
	private long boardId;

	public Like toEntity(Users users, Board board) {
		return new Like(users, board);
	}
}
