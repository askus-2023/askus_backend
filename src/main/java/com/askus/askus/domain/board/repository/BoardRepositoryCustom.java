package com.askus.askus.domain.board.repository;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;

public interface BoardRepositoryCustom {
	List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request);
}
