package com.askus.askus.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.image.repository.BoardImageRepository;
import com.askus.askus.domain.image.service.ImageService;
import com.askus.askus.domain.reply.dto.RepliesSearchResponse;
import com.askus.askus.domain.reply.repository.ReplyRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final UsersRepository usersRepository;
	private final ReplyRepository replyRepository;
	private final BoardImageRepository boardImageRepository;
	private final ImageService imageService;

	@Override
	public BoardResponse.Post addBoard(long userId, BoardRequest.Post request) {
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new KookleRuntimeException("존재하지 않는 사용자입니다."));

		Board board = boardRepository.save(request.toEntity(users));

		BoardImage thumbnailImage = imageService.uploadThumbnailImage(board, request.getThumbnailImage());
		List<BoardImage> representativeImages = request.getRepresentativeImages().stream()
			.map(image -> imageService.uploadRepresentativeImage(board, image))
			.collect(Collectors.toList());

		return BoardResponse.Post.ofEntity(board, users, thumbnailImage, representativeImages);
	}

	@Override
	public List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request) {
		return boardRepository.searchBoards(request);
	}

	@Override
	public BoardResponse.Detail searchBoard(long boardId) {
		Board board = boardRepository.findByIdAndDeletedAtNull(boardId)
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		List<BoardImage> representativeImages = boardImageRepository.findAllByBoardAndImageTypeAndDeletedAtNull(
			board, ImageType.REPRESENTATIVE
		);

		List<RepliesSearchResponse> replies = replyRepository.findAllByBoardAndDeletedAtNull(board).stream()
			.map(reply -> new RepliesSearchResponse(reply.getUsers().getNickname(), reply.getContent(),
				reply.getCreatedAt()))
			.collect(Collectors.toList());

		return BoardResponse.Detail.ofEntity(board.getUsers(), board, representativeImages, replies);
	}

	@Override
	public BoardResponse.Post updateBoard(long boardId, BoardRequest.Post request) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		board.update(
			request.getTitle(),
			request.getCategory(),
			request.getIngredients(),
			request.getContent(),
			request.getTag()
		);

		Board savedBoard = boardRepository.save(board);

		BoardImage thumbnailImage = imageService.uploadThumbnailImage(board, request.getThumbnailImage());
		List<BoardImage> representativeImages = request.getRepresentativeImages().stream()
			.map(image -> imageService.uploadRepresentativeImage(board, image))
			.collect(Collectors.toList());

		// return BoardResponse.Post.ofEntity(board, users, thumbnailImage, representativeImages);
		return null;
	}

	@Override
	public BoardResponse.Delete deleteBoard(BoardRequest.Delete request) {
		List<Board> deletedBoards = new ArrayList<>();

		List<Board> boards = boardRepository.findAllById(request.getBoardIds());
		for (Board board : boards) {
			board.delete();
			deletedBoards.add(board);
		}

		List<Long> boardIds = boardRepository.saveAll(deletedBoards).stream()
			.map(board -> board.getId())
			.collect(Collectors.toList());

		return BoardResponse.Delete.ofEntity(boardIds);
	}
}
