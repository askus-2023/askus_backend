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
import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
	private final LikeRepository likeRepository;
	private final UsersRepository usersRepository;
	private final BoardRepository boardRepository;

	@Override
	@Transactional
	public LikeResponse addLike(long usersId, LikeRequest request) {
		// 1. find users
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new KookleRuntimeException("users not found: " + usersId));

		// 2. find board
		Board board = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new KookleRuntimeException("board not found: " + request.getBoardId()));

		// 3. validate
		Optional<Like> optionalLike = likeRepository.findByUsersAndBoard(users, board);
		if (optionalLike.isPresent()) {
			throw new KookleRuntimeException("like entity already exists: " + optionalLike.get().getId());
		}

		// 4. save
		likeRepository.save(request.toEntity(users, board));

		// 5. update board
		board.addLikeCount();

		// 6. return
		return LikeResponse.ofEntity(board);
	}

	@Override
	@Transactional
	public LikeResponse deleteLike(long usersId, LikeRequest request) {
		// 1. find users
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new KookleRuntimeException("users not found: " + usersId));

		// 2. find board
		Board board = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new KookleRuntimeException("board not found: " + request.getBoardId()));

		// 3. find like
		Like like = likeRepository.findByUsersAndBoard(users, board)
			.orElseThrow(() -> new KookleRuntimeException("like not found"));

		// 4. delete & update board
		likeRepository.delete(like);
		board.deleteLikeCount();

		// 5. return
		return LikeResponse.ofEntity(board);
	}
}
