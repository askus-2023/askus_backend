package com.askus.askus.domain.board.repository;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;

public interface BoardRepositoryCustom {

	/**
	 * search boards by given request(BoardRequest.Summary)
	 *
	 * @param request - BoardRequest.Summary
	 * @return - searched boards data list(BoardResponse.Summary)
	 */
	List<BoardResponse.Summary> searchBoards(long userId, BoardRequest.Summary request);

	List<BoardResponse.Summary> searchBoardsByType(String boardType, Long userId);

	/**
	 * search board by given request(boardId)
	 *
	 * @param userId - security user id
	 * @param boardId - selected board id
	 * @return - searched board data(BoardResponse.Detail)
	 */
	BoardResponse.Detail searchBoard(long userId, long boardId);
}
