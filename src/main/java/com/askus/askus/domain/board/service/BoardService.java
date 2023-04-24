package com.askus.askus.domain.board.service;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;

public interface BoardService {
	BoardResponse.Post addBoard(long userId, BoardRequest.Post request);

	List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request);
	List<BoardResponse.Summary> searchBoardsByType(String boardType, Long userId);

	BoardResponse.Detail searchBoard(long boardId);

	BoardResponse.Post updateBoard(long userId, long boardId, BoardRequest.Post request);

	void deleteBoard(BoardRequest.Delete request);
}
