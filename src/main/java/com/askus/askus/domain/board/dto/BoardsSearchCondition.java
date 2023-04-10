package com.askus.askus.domain.board.dto;

import java.time.LocalDateTime;

import com.askus.askus.global.util.SortConditions;

import lombok.Getter;

@Getter
public class BoardsSearchCondition {
	/* 검색조건 */
	private String tag;
	private LocalDateTime dateLoe;
	private LocalDateTime dateGoe;
	/* 정렬기준 */
	private SortConditions sortTarget;

	public BoardsSearchCondition(
		String tag,
		LocalDateTime dateLoe,
		LocalDateTime dateGoe,
		String sortTarget) {
		this.tag = tag;
		this.dateLoe = dateLoe;
		this.dateGoe = dateGoe;
		this.sortTarget = SortConditions.valueOf(sortTarget);
	}
}
