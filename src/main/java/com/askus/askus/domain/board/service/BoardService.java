package com.askus.askus.domain.board.service;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;

public interface BoardService {
	BoardResponse.Post addBoard(long userId, BoardRequest.Post request);

	List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request);

	List<BoardResponse.Summary> searchBoardsByUsers(Long userId);

	List<BoardResponse.Summary> searchBoardsByLiked(Long userId);

	BoardResponse.Detail searchBoard(long boardId);

	BoardResponse.Patch updateBoard(long userId, long boardId, BoardRequest.Patch request);

	BoardResponse.Delete deleteBoard(BoardRequest.Delete request);
}
