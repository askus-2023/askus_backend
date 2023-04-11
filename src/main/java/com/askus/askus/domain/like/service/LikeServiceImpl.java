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

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
	private final LikeRepository likeRepository;
	private final UsersRepository usersRepository;
	private final BoardRepository boardRepository;

	@Override
	@Transactional
	public LikeResponse addLike(long usersId, LikeRequest request) {
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new KookleRuntimeException("Users Not Found"));

		Board board = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		Optional<Like> optionalLike = likeRepository.findByUsersAndBoard(users, board);

		if (optionalLike.isPresent()) {
			throw new KookleRuntimeException("이미 존재하는 좋아요");
		}

		likeRepository.save(request.toEntity(users, board));

		board.addLikeCount();
		boardRepository.save(board);

		return LikeResponse.ofEntity(board);
	}

	@Override
	@Transactional
	public LikeResponse deleteLike(long usersId, LikeRequest request) {
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new KookleRuntimeException("Users Not Found"));

		Board board = boardRepository.findById(request.getBoardId())
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		Like like = likeRepository.findByUsersAndBoard(users, board)
			.orElseThrow(() -> new KookleRuntimeException("Like Not Found"));

		likeRepository.delete(like);

		board.deleteLikeCount();
		boardRepository.save(board);

		return LikeResponse.ofEntity(board);
	}
}
