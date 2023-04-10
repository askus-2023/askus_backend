package com.askus.askus.domain.board.service;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;

public interface BoardService {
	BoardResponse.Post addBoard(long userId, BoardRequest.Post request);

	List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request);

	BoardResponse.Detail searchBoard(long boardId);

	BoardResponse.Post updateBoard(long boardId, BoardRequest.Post request);

	BoardResponse.Delete deleteBoard(BoardRequest.Delete request);
}
