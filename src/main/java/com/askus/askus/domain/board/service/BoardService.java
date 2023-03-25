package com.askus.askus.domain.board.service;

import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.board.dto.BoardAddResponse;

public interface BoardService {
	BoardAddResponse addBoard(long id, BoardAddRequest request);
}
