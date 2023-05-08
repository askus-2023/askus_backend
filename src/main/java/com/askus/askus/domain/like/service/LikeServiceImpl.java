package com.askus.askus.domain.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.like.domain.Like;
import com.askus.askus.domain.like.dto.LikeRequest;
import com.askus.askus.domain.like.dto.LikeResponse;
import com.askus.askus.domain.like.repository.LikeRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.error.exception.AlreadyExistException;
import com.askus.askus.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
	private final LikeRepository likeRepository;
	private final UsersRepository usersRepository;
	private final BoardRepository boardRepository;

	@Transactional
	@Override
	public LikeResponse addLike(long usersId, LikeRequest request) {
		// 1. find users
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new NotFoundException("users", usersId));

		// 2. find board
		Board board = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new NotFoundException("board", request.getBoardId()));

		// 3. validate
		Optional<Like> optionalLike = likeRepository.findByUsersAndBoard(users, board);
		if (optionalLike.isPresent()) {
			throw new AlreadyExistException("like", users.getId(), board.getId());
		}

		// 4. save
		likeRepository.save(request.toEntity(users, board));

		// 5. update board
		board.addLikeCount();

		// 6. return
		return LikeResponse.ofEntity(board);
	}

	@Transactional
	@Override
	public LikeResponse deleteLike(long usersId, LikeRequest request) {
		// 1. find users
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new NotFoundException("users", usersId));

		// 2. find board
		Board board = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new NotFoundException("board", request.getBoardId()));

		// 3. find like
		Like like = likeRepository.findByUsersAndBoard(users, board)
			.orElseThrow(() -> new NotFoundException("like", users.getId(), board.getId()));

		// 4. delete & update board
		likeRepository.delete(like);
		board.deleteLikeCount();

		// 5. return
		return LikeResponse.ofEntity(board);
	}
}
