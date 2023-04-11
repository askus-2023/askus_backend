package com.askus.askus.domain.board.repository;

import static com.askus.askus.domain.board.domain.QBoard.*;
import static com.askus.askus.domain.image.domain.QBoardImage.*;
import static com.askus.askus.domain.users.domain.QUsers.*;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;
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
	public List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request) {
		return queryFactory
			.select(Projections.constructor(BoardResponse.Summary.class,
				board.id,
				users.nickname,
				board.createdAt,
				ExpressionUtils.as(
					JPAExpressions
						.select(boardImage.url).from(boardImage)
						.where(
							boardImage.board.id.eq(board.id),
							boardImage.imageType.eq(ImageType.THUMBNAIL),
							boardImage.deletedAt.isNull()), "url"
				),
				board.likeCount,
				board.replyCount
			))
			.from(board)
			.join(board.users, users)
			.where(
				searchTag(request),
				dateLoe(request),
				dateGoe(request),
				board.deletedAt.isNull()
			)
			.orderBy(sort(request))
			.distinct().fetch();
	}

	private BooleanExpression searchTag(BoardRequest.Summary request) {
		if (StringUtil.isNullOrEmpty(request.getTag())) {
			return null;
		}
		return board.tag.like("%" + request.getTag() + "%");
	}

	private BooleanExpression dateLoe(BoardRequest.Summary request) {
		if (request.getDateLoe().isEmpty()) {
			return null;
		}
		return board.createdAt.after(request.getDateLoe().get());
	}

	private BooleanExpression dateGoe(BoardRequest.Summary request) {
		if (request.getDateGoe().isEmpty()) {
			return null;
		}
		return board.createdAt.before(request.getDateGoe().get());
	}

	private OrderSpecifier<?> sort(BoardRequest.Summary request) {
		if (request.getSortTarget().equals(SortConditions.CREATED_AT_DESC)) {
			return board.createdAt.desc();
		} else if (request.getSortTarget().equals(SortConditions.CREATED_AT_ASC)) {
			return board.createdAt.asc();
		} else if (request.getSortTarget().equals(SortConditions.LIKE_COUNT_DESC)) {
			return board.likeCount.desc();
		} else {
			return board.replyCount.desc();
		}
	}
}
