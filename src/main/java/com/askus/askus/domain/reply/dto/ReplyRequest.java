package com.askus.askus.domain.reply.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReplyRequest {
	@Getter
	@AllArgsConstructor
	public static class Post {
		private final String content;

		public Reply toEntity(Users users, Board board) {
			return new Reply(users, board, this.content);
		}
	}

	@Getter
	public static class Patch {

	}

	@Getter
	public static class Delete {

	}
}
