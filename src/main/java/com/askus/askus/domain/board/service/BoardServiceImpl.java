package com.askus.askus.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public BoardResponse.Post addBoard(long userId, BoardRequest.Post request) {
		// 1. find user
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new KookleRuntimeException("user not found: " + userId));

		// 2. save board
		Board board = boardRepository.save(request.toEntity(users));

		// 3. save images
		Optional<BoardImage> thumbnailImage = Optional.empty();
		Optional<List<BoardImage>> representativeImages = Optional.empty();
		if (request.getThumbnailImage().isPresent()) {
			thumbnailImage = Optional.ofNullable(
				imageService.uploadThumbnailImage(board, request.getThumbnailImage().get()));
		}
		if (request.getRepresentativeImages().isPresent()) {
			representativeImages = Optional.of(request.getRepresentativeImages().get().stream()
				.map(image -> imageService.uploadRepresentativeImage(board, image))
				.collect(Collectors.toList()));
		}

		// 4. return
		return BoardResponse.Post.ofEntity(board, users, thumbnailImage, representativeImages);
	}

	@Override
	public List<BoardResponse.Summary> searchBoards(BoardRequest.Summary request) {
		// get & return
		return boardRepository.searchBoards(request);
	}

	@Override
	public BoardResponse.Detail searchBoard(long boardId) {
		// 1. find board
		Board board = boardRepository.findByIdAndDeletedAtNull(boardId)
			.orElseThrow(() -> new KookleRuntimeException("board not found: " + boardId));

		// 2. find images
		Optional<BoardImage> thumbnailImage = boardImageRepository.findByBoardAndImageTypeAndDeletedAtNull(
			board, ImageType.THUMBNAIL);
		List<BoardImage> representativeImages = boardImageRepository.findAllByBoardAndImageTypeAndDeletedAtNull(
			board, ImageType.REPRESENTATIVE);

		// 3. find replies
		List<ReplyResponse.Summary> replies = replyRepository.findAllByBoardAndDeletedAtNull(board).stream()
			.map(reply -> ReplyResponse.Summary.ofEntity(board.getUsers(), reply))
			.collect(Collectors.toList());

		// 4. return
		return BoardResponse.Detail.ofEntity(board.getUsers(), board, thumbnailImage, representativeImages, replies);
	}

	@Override
	@Transactional
	public BoardResponse.Patch updateBoard(long userId, long boardId, BoardRequest.Patch request) {
		// 1. find users
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new KookleRuntimeException("user not found: " + userId));

		// 2. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new KookleRuntimeException("board not found: " + boardId));

		// 3. update
		request.update(board);

		// 4. update images
		Optional<BoardImage> thumbnailImage = Optional.empty();
		Optional<List<BoardImage>> representativeImages = Optional.empty();
		if (request.getThumbnailImage().isPresent()) {
			imageService.deleteThumbnailImage(board);
			thumbnailImage = Optional.ofNullable(imageService.uploadThumbnailImage(board, request.getThumbnailImage().get()));
		} else {
			Optional<BoardImage> optionalImage = boardImageRepository.findByBoardAndImageTypeAndDeletedAtNull(board, ImageType.THUMBNAIL);
			if (optionalImage.isPresent()) {
				thumbnailImage = Optional.of(optionalImage.get());
			}
		}
		if (request.getRepresentativeImages().isPresent()) {
			imageService.deleteRepresentativeImages(board);
			representativeImages = Optional.of(request.getRepresentativeImages().get().stream()
				.map(image -> imageService.uploadRepresentativeImage(board, image))
				.collect(Collectors.toList()));
		} else {
			representativeImages = Optional.ofNullable(
				boardImageRepository.findAllByBoardAndImageTypeAndDeletedAtNull(board,
					ImageType.REPRESENTATIVE));
		}

		// 5. return
		return BoardResponse.Patch.ofEntity(board, user, thumbnailImage, representativeImages);
	}

	@Override
	@Transactional
	public void deleteBoard(BoardRequest.Delete request) {
		// find & delete
		List<Board> deletedBoards = new ArrayList<>();

		List<Board> boards = boardRepository.findAllById(request.getBoardIds());
		for (Board board : boards) {
			board.delete();
			deletedBoards.add(board);
		}
	}
}
