package com.askus.askus.domain.reply.dto;

import java.time.LocalDateTime;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReplyResponse {
	@Getter
	@AllArgsConstructor
	public static class Post {
		private final long boardId;
		private final String content;
		private final long replyCount;

		public static Post ofEntity(Board board, Reply reply) {
			return new Post(board.getId(), reply.getContent(), board.getReplyCount());
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Summary {
		private final String replyAuthor;
		private final String content;
		private final LocalDateTime createdAt;

		public static Summary ofEntity(Users users, Reply reply) {
			return new Summary(users.getNickname(), reply.getContent(), reply.getCreatedAt());
		}
	}

	@Getter
	public static class Patch {

	}

	@Getter
	public static class Delete {

	}
}
