package com.askus.askus.domain.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.RepresentativeImage;
import com.askus.askus.domain.image.domain.ThumbnailImage;
import com.askus.askus.domain.image.service.ImageUploader;
import com.askus.askus.domain.reply.repository.ReplyRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final UsersRepository usersRepository;
	private final ReplyRepository replyRepository;
	private final ImageUploader imageUploader;

	@Override
	@Transactional
	public BoardResponse.Post addBoard(long userId, BoardRequest.Post request) {
		// 1. find user
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));

		// 2. save board
		Board board = boardRepository.save(request.toEntity(users));

		// 3. save images
		uploadImages(board, request.getThumbnailImage(), request.getRepresentativeImages());

		// 4. return
		return BoardResponse.Post.ofEntity(board, users);
	}

	@Override
	public List<BoardResponse.Summary> searchBoards(long userId, BoardRequest.Summary request) {
		// get & return
		return boardRepository.searchBoards(userId, request);
	}

	@Override
	public List<BoardResponse.Summary> searchBoardsByType(String boardType, long userId) {
		return boardRepository.searchBoardsByType(boardType, userId);
	}

	@Override
	public BoardResponse.Detail searchBoard(long userId, long boardId) {
		// 1. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("board", boardId));

		// 2. find datas for board detail
		BoardResponse.Detail response = boardRepository.searchBoard(userId, boardId);

		// 3. find representativeImages & set
		List<String> representativeImageUrls = boardRepository.findRepresentativeImagesByBoardId(boardId).stream()
			.map(representativeImage -> representativeImage.getUrl())
			.collect(Collectors.toList());
		response.setRepresentativeImageUrls(representativeImageUrls);

		// 4. return
		return boardRepository.searchBoard(userId, boardId);
	}

	@Override
	@Transactional
	public BoardResponse.Post updateBoard(long userId, long boardId, BoardRequest.Post request) {
		// 1. find users
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));

		// 2. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("board", boardId));

		// 3. update board
		board.update(request);

		// 4. update images
		board.resetThumbnailImage();
		board.resetRepresentativeImages();
		uploadImages(board, request.getThumbnailImage(), request.getRepresentativeImages());

		// 5. return
		return BoardResponse.Post.ofEntity(board, user);
	}

	private void uploadImages(
		Board board,
		Optional<Image> optionalThumbnailImage,
		Optional<List<Image>> optionalRepresentativeImages
	) {
		if (optionalThumbnailImage.isPresent()) {
			String thumbnailImageUrl = optionalThumbnailImage.get().uploadBy(imageUploader);
			ThumbnailImage thumbnailImage = new ThumbnailImage(board, thumbnailImageUrl);
			board.setThumbnailImage(thumbnailImage);
		}
		if (optionalRepresentativeImages.isPresent()) {
			List<RepresentativeImage> representativeImages = optionalRepresentativeImages.get().stream()
				.map(image -> new RepresentativeImage(board, image.uploadBy(imageUploader)))
				.collect(Collectors.toList());
			representativeImages.stream()
				.forEach(representativeImage -> board.addRepresentativeImage(representativeImage));
		}
	}

	@Override
	@Transactional
	public void deleteBoard(BoardRequest.Delete request) {
		// find & delete
		boardRepository.findAllById(request.getBoardIds()).stream().forEach(board -> board.delete());
	}
}
