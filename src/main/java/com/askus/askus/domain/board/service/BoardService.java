package com.askus.askus.domain.board.service;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.board.dto.BoardAddResponse;
import com.askus.askus.domain.board.dto.BoardsSearchCondition;
import com.askus.askus.domain.board.dto.BoardsSearchResponse;

public interface BoardService {
	BoardAddResponse addBoard(long id, BoardAddRequest request);

	List<BoardsSearchResponse> searchBoards(BoardsSearchCondition condition);
}
