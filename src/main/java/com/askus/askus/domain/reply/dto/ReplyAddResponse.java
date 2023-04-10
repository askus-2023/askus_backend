package com.askus.askus.domain.reply.dto;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.reply.domain.Reply;

import lombok.Getter;

@Getter
public class ReplyAddResponse {
	private final long boardId;
	private final String content;
	private final long replyCount;

	public ReplyAddResponse(long boardId, String content, long replyCount) {
		this.boardId = boardId;
		this.content = content;
		this.replyCount = replyCount;
	}

	public static ReplyAddResponse ofEntity(Board board, Reply reply) {
		return new ReplyAddResponse(board.getId(), reply.getContent(), board.getReplyCount());
	}
}
