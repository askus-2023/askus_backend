package com.askus.askus.domain.reply.dto;

import java.time.LocalDateTime;

import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

@Getter
public class RepliesSearchResponse {
	private final String replyAuthor;
	private final String content;
	private final LocalDateTime createdAt;

	public RepliesSearchResponse(String replyAuthor, String content, LocalDateTime createdAt) {
		this.replyAuthor = replyAuthor;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static RepliesSearchResponse ofEntity(Users users, Reply reply) {
		return new RepliesSearchResponse(users.getNickname(), reply.getContent(), reply.getCreatedAt());
	}
}
