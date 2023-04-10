package com.askus.askus.domain.board.repository;

import static com.askus.askus.domain.board.domain.QBoard.*;
import static com.askus.askus.domain.image.domain.QBoardImage.*;

import java.util.List;
import java.util.Objects;

import com.askus.askus.domain.board.dto.BoardsSearchCondition;
import com.askus.askus.domain.board.dto.BoardsSearchResponse;
import com.askus.askus.global.util.StringUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardsSearchResponse> searchBoards(BoardsSearchCondition condition) {
		//TODO: board에 댓글수, 좋아요수 추가
		return queryFactory
			.select(Projections.constructor(BoardsSearchResponse.class,
				board.id,
				board.users,
				board.createdAt,
				boardImage.url
			))
			.from(board)
			.innerJoin(boardImage.board, board)
			.on(boardImage.board.id.eq(board.id))
			.where(
				searchTag(condition),
				dateLoe(condition),
				dateGoe(condition),
				board.deletedAt.isNull()
			)
			.orderBy(sort(condition))
			.fetch();
	}

	private BooleanExpression searchTag(BoardsSearchCondition condition) {
		if (StringUtil.isNullOrEmpty(condition.getTag())) {
			return null;
		}
		return board.tag.like("%" + condition.getTag() + "%");
	}

	private BooleanExpression dateLoe(BoardsSearchCondition condition) {
		if (Objects.isNull(condition.getDateLoe())) {
			return null;
		}
		return board.createdAt.after(condition.getDateLoe());
	}

	private BooleanExpression dateGoe(BoardsSearchCondition condition) {
		if (Objects.isNull(condition.getDateGoe())) {
			return null;
		}
		return board.createdAt.before(condition.getDateGoe());
	}

	private OrderSpecifier<?> sort(BoardsSearchCondition condition) {
		return board.createdAt.desc(); //TODO: 정렬기준에 따른 구분 적용
	}
}
