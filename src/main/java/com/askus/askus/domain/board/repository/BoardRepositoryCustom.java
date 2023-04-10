package com.askus.askus.domain.board.repository;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardsSearchCondition;
import com.askus.askus.domain.board.dto.BoardsSearchResponse;

public interface BoardRepositoryCustom {
	List<BoardsSearchResponse> searchBoards(BoardsSearchCondition condition);
}
