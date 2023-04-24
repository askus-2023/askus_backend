package com.askus.askus.domain.reply.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {
	private String content;

	public Reply toEntity(Users users, Board board) {
		return new Reply(users, board, this.content);
	}

	public void update(Reply reply) {
		reply.update(this.content);
	}
}
