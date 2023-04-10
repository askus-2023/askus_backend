package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardsSearchResponse {
	private long id;
	private String author;
	private LocalDateTime createdAt;
	private String url;
	private long likeCount;
	private long replyCount;

	public BoardsSearchResponse(
		long id,
		String author,
		LocalDateTime createdAt,
		String url,
		long likeCount,
		long replyCount
	) {
		this.id = id;
		this.author = author;
		this.createdAt = createdAt;
		this.url = url;
		this.likeCount = likeCount;
		this.replyCount = replyCount;
	}
}
