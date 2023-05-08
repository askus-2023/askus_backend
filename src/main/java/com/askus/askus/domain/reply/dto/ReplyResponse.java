package com.askus.askus.domain.reply.dto;

import java.time.LocalDateTime;

import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

/**
 * Response DTO for domain Reply
 * */
@Getter
public class ReplyResponse {
	private final long replyId;
	private final String replyAuthor;
	private final String content;
	private final LocalDateTime createdAt;
	private final boolean myReply;
	private final String authorProfileImageUrl;

	public ReplyResponse(
		long replyId,
		String replyAuthor,
		String content,
		LocalDateTime createdAt,
		boolean myReply,
		String authorProfileImageUrl) {
		this.replyId = replyId;
		this.replyAuthor = replyAuthor;
		this.content = content;
		this.createdAt = createdAt;
		this.myReply = myReply;
		this.authorProfileImageUrl = authorProfileImageUrl;
	}

	public static ReplyResponse ofEntity(Users users, Reply reply) {
		return new ReplyResponse(
			reply.getId(),
			users.getNickname(),
			reply.getContent(),
			reply.getCreatedAt(),
			reply.getUsers().getId().equals(users.getId()),
			reply.getUsers().getProfileImage().getUrl());
	}
}
