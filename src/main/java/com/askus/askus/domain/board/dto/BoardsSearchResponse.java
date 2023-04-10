package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardsSearchResponse {
	private long id;
	private String author;
	private LocalDateTime createdAt;
	private String url;

	public BoardsSearchResponse(
		long id,
		String author,
		LocalDateTime createdAt,
		String url
	) {
		this.id = id;
		this.author = author;
		this.createdAt = createdAt;
		this.url = url;
	}
}
