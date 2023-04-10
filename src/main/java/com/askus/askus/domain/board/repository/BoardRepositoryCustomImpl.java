package com.askus.askus.domain.board.repository;

import static com.askus.askus.domain.board.domain.QBoard.*;
import static com.askus.askus.domain.image.domain.QBoardImage.*;
import static com.askus.askus.domain.users.domain.QUsers.*;

import java.util.List;
import java.util.Objects;

import com.askus.askus.domain.board.dto.BoardsSearchCondition;
import com.askus.askus.domain.board.dto.BoardsSearchResponse;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.global.util.SortConditions;
import com.askus.askus.global.util.StringUtil;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardsSearchResponse> searchBoards(BoardsSearchCondition condition) {
		return queryFactory
			.select(Projections.constructor(BoardsSearchResponse.class,
				board.id,
				users.nickname,
				board.createdAt,
				ExpressionUtils.as(
					JPAExpressions
						.select(boardImage.url).from(boardImage)
						.where(boardImage.board.id.eq(board.id), boardImage.imageType.eq(ImageType.THUMBNAIL)), "url"
				),
				board.likeCount,
				board.replyCount
			))
			.from(board)
			.join(board.users, users)
			.where(
				searchTag(condition),
				dateLoe(condition),
				dateGoe(condition),
				board.deletedAt.isNull()
			)
			.orderBy(sort(condition))
			.distinct().fetch();
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
		if (condition.getSortTarget().equals(SortConditions.CREATED_AT_DESC)) {
			return board.createdAt.desc();
		} else if (condition.getSortTarget().equals(SortConditions.CREATED_AT_ASC)) {
			return board.createdAt.asc();
		} else if (condition.getSortTarget().equals(SortConditions.LIKE_COUNT_DESC)) {
			return board.likeCount.desc();
		} else {
			return board.replyCount.desc();
		}
	}
}
