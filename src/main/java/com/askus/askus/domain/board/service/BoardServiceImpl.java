package com.askus.askus.domain.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.board.dto.BoardAddResponse;
import com.askus.askus.domain.board.dto.BoardsSearchCondition;
import com.askus.askus.domain.board.dto.BoardsSearchResponse;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.image.service.ImageService;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final UsersRepository usersRepository;
	private final ImageService imageService;

	@Override
	public BoardAddResponse addBoard(long id, BoardAddRequest request) {
		Users users = usersRepository.findById(id)
			.orElseThrow(() -> new KookleRuntimeException("존재하지 않는 사용자"));

		Board board = boardRepository.save(request.toEntity(users));

		Map<ImageType, Object> map = imageService.uploadBoardImage(board, request);
		BoardImage thumbnailImage = (BoardImage) map.get(ImageType.THUMBNAIL);
		BoardImage representativeImage = (BoardImage) map.get(ImageType.REPRESENTATIVE);

		return BoardAddResponse.ofEntity(board, users, thumbnailImage, representativeImage);
	}

	@Override
	public List<BoardsSearchResponse> searchBoards(BoardsSearchCondition condition) {
		return boardRepository.searchBoards(condition);
	}
}
