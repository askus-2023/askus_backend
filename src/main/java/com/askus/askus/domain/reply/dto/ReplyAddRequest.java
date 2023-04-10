package com.askus.askus.domain.reply.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

@Getter
public class ReplyAddRequest {
	private String content;

	public ReplyAddRequest(String content) {
		this.content = content;
	}

	public Reply toEntity(Users users, Board board) {
		return new Reply(users, board, this.content);
	}
}
