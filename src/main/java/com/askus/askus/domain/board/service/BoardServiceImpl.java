package com.askus.askus.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.askus.askus.domain.reply.dto.ReplyResponse;
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

		BoardImage thumbnailImage = null;
		List<BoardImage> representativeImages = null;
		if (request.getThumbnailImage().isPresent()) {
			thumbnailImage = imageService.uploadThumbnailImage(board, request.getThumbnailImage().get());
		}
		if (request.getRepresentativeImages().isPresent()) {
			representativeImages = request.getRepresentativeImages().get().stream()
				.map(image -> imageService.uploadRepresentativeImage(board, image))
				.collect(Collectors.toList());
		}

		return BoardResponse.Post.ofEntity(board, users, Optional.ofNullable(thumbnailImage),
			Optional.ofNullable(representativeImages));
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

		List<ReplyResponse.Summary> replies = replyRepository.findAllByBoardAndDeletedAtNull(board).stream()
			.map(reply -> ReplyResponse.Summary.ofEntity(board.getUsers(), reply))
			.collect(Collectors.toList());

		return BoardResponse.Detail.ofEntity(board.getUsers(), board, representativeImages, replies);
	}

	@Override
	public BoardResponse.Patch updateBoard(long userId, long boardId, BoardRequest.Patch request) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new KookleRuntimeException("User Not Found"));

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		request.update(board);
		boardRepository.save(board);

		BoardImage thumbnailImage;
		List<BoardImage> representativeImages;
		if (request.getThumbnailImage().isPresent()) {
			imageService.deleteThumbnailImage(board);
			thumbnailImage = imageService.uploadThumbnailImage(board, request.getThumbnailImage().get());
		} else {
			thumbnailImage = boardImageRepository.findByBoardAndImageTypeAndDeletedAtNull(board, ImageType.THUMBNAIL)
				.orElseThrow(() -> new KookleRuntimeException("thumbnail image not found"));
		}
		if (request.getRepresentativeImages().isPresent()) {
			imageService.deleteRepresentativeImages(board);
			representativeImages = request.getRepresentativeImages().get().stream()
				.map(image -> imageService.uploadRepresentativeImage(board, image))
				.collect(Collectors.toList());
		} else {
			representativeImages = boardImageRepository.findAllByBoardAndImageTypeAndDeletedAtNull(board,
				ImageType.REPRESENTATIVE);
		}

		return BoardResponse.Patch.ofEntity(board, user, thumbnailImage, representativeImages);
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
