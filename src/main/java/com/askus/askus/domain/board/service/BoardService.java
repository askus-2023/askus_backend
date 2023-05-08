package com.askus.askus.domain.board.service;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;

public interface BoardService {

	/**
	 * add board by given request(BoardRequest.Post)
	 *
	 * @param userId - current userId
	 * @param request - BoardRequest.Post
	 * @return - added board data(BoardResponse.Post)
	 */
	BoardResponse.Post addBoard(long userId, BoardRequest.Post request);

	/**
	 * search boards by given request(BoardRequest.Summary)
	 *
	 * @param request - BoardRequest.Summary
	 * @return - searched boards data list(BoardResponse.Summary)
	 */
	List<BoardResponse.Summary> searchBoards(long userId, BoardRequest.Summary request);

	List<BoardResponse.Summary> searchBoardsByType(String boardType, long userId);

	/**
	 * search board by given request
	 *
	 * @param boardId - selected boardId
	 * @return - searched board data(BoardResponse.Detail)
	 */
	BoardResponse.Detail searchBoard(long userId, long boardId);

	/**
	 * update board by given request(BoardRequest.Post)
	 *
	 * @param userId - current userId
	 * @param boardId - selected boardId
	 * @param request - BoardRequest.Post
	 * @return - updated board data(BoardResponse.Post)
	 */
	BoardResponse.Post updateBoard(long userId, long boardId, BoardRequest.Post request);

	/**
	 * delete boards by given request(BoardRequest.Delete)
	 *
	 * @param request - BoardRequest.Delete
	 */
	void deleteBoard(BoardRequest.Delete request);
}
