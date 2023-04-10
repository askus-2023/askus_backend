package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardsSearchCondition {
	/* 검색조건 */
	private String tag;
	private LocalDateTime dateLoe;
	private LocalDateTime dateGoe;
	/* 정렬기준 */
	//TODO: 정렬기준 공통으로 추출해 util 클래스 생성
	private String sortTarget;

	public BoardsSearchCondition(
		String tag,
		LocalDateTime dateLoe,
		LocalDateTime dateGoe,
		String sortTarget) {
		this.tag = tag;
		this.dateLoe = dateLoe;
		this.dateGoe = dateGoe;
		this.sortTarget = sortTarget;
	}
}
