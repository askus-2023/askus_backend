package com.askus.askus.domain.board.repository;

import static com.askus.askus.domain.board.domain.QBoard.*;
import static com.askus.askus.domain.image.domain.QThumbnailImage.*;
import static com.askus.askus.domain.like.domain.QLike.*;
import static com.askus.askus.domain.users.domain.QUsers.*;

import java.util.List;

import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.global.util.SortConditions;
import com.askus.askus.global.util.StringUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardResponse.Summary> searchBoards(long userId, BoardRequest.Summary request) {

		List<Long> likeIds = getLikeIds(userId);

		return queryFactory
			.select(Projections.constructor(BoardResponse.Summary.class,
				board.id,
				board.foodName,
				board.category,
				new CaseBuilder()
					.when(board.id.in(likeIds)).then(true)
					.otherwise(false),
				users.profileImage.url,
				board.title,
				users.nickname,
				board.createdAt,
				thumbnailImage.url,
				board.likeCount,
				board.replyCount
			))
			.from(board)
			.innerJoin(board.users, users)
			.leftJoin(board.thumbnailImage, thumbnailImage)
			.where(
				searchTag(request),
				dateLoe(request),
				dateGoe(request),
				board.deletedAt.isNull()
			)
			.orderBy(sort(request))
			.distinct().fetch();
	}

	@Override
	public List<BoardResponse.Summary> searchBoardsByType(String boardType, Long userId) {

		List<Long> likeIds = getLikeIds(userId);

		BooleanExpression predicate = board.users.id.eq(userId);
		if (boardType.equals("like")) {
			predicate = board.id.in(likeIds);
		}

		return queryFactory
			.select(Projections.constructor(BoardResponse.Summary.class,
				board.id,
				board.foodName,
				board.category,
				new CaseBuilder()
					.when(board.id.in(likeIds)).then(true)
					.otherwise(false),
				users.profileImage.url,
				board.title,
				users.nickname,
				board.createdAt,
				thumbnailImage.url,
				board.likeCount,
				board.replyCount
			))
			.from(board)
			.innerJoin(board.users, users)
			.leftJoin(board.thumbnailImage, thumbnailImage)
			.where(
				predicate,
				board.deletedAt.isNull()
			)
			.orderBy(board.createdAt.desc())
			.distinct().fetch();
	}

	@Override
	public BoardResponse.Detail searchBoard(long userId, long boardId) {
		List<Long> likeIds = getLikeIds(userId);

		return queryFactory.select(Projections.constructor(BoardResponse.Detail.class,
				board.title,
				board.foodName,
				users.nickname,
				new CaseBuilder()
					.when(users.id.eq(userId)).then(true)
					.otherwise(false),
				board.ingredients,
				board.category,
				board.content,
				board.createdAt,
				thumbnailImage.url,
				board.tag,
				board.likeCount,
				new CaseBuilder()
					.when(board.id.in(likeIds)).then(true)
					.otherwise(false)
			))
			.from(board)
			.innerJoin(board.users, users)
			.innerJoin(board.thumbnailImage, thumbnailImage)
			.where(board.id.eq(boardId))
			.distinct().fetchOne();
	}

	private List<Long> getLikeIds(long userId) {
		return queryFactory
			.select(like.board.id)
			.from(like)
			.where(like.users.id.eq(userId))
			.fetch();
	}

	private BooleanExpression searchTag(BoardRequest.Summary request) {
		if (StringUtil.isNullOrEmpty(request.getTag())) {
			return null;
		}
		return board.tag.like("%" + request.getTag() + "%").or(board.category.eq(Category.valueOf(request.getTag())));
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
