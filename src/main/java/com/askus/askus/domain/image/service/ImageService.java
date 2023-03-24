package com.askus.askus.domain.image.service;

import java.io.IOException;
import java.util.Map;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.image.domain.ImageType;

public interface ImageService {
	Map<ImageType, Object> uploadBoardImage(Board board, BoardAddRequest request) throws IOException;
}
