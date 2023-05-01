package com.askus.askus.domain.reply.dto;

import java.time.LocalDateTime;

import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;

@Getter
public class ReplyResponse {
	private final String replyAuthor;
	private final String content;
	private final LocalDateTime createdAt;
	private final boolean myReply;
	private final String authorProfileImageUrl;

	public ReplyResponse(String replyAuthor, String content, LocalDateTime createdAt, boolean myReply,
		String authorProfileImageUrl) {
		this.replyAuthor = replyAuthor;
		this.content = content;
		this.createdAt = createdAt;
		this.myReply = myReply;
		this.authorProfileImageUrl = authorProfileImageUrl;
	}

	public static ReplyResponse ofEntity(Users users, Reply reply) {
		return new ReplyResponse(users.getNickname(), reply.getContent(), reply.getCreatedAt(),
			reply.getUsers().getId().equals(users.getId()), reply.getUsers().getProfileImage().getUrl());
	}
}
