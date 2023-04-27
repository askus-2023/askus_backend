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
	List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request);

	List<BoardResponse.Summary> searchBoardsByType(String boardType, Long userId);
}
