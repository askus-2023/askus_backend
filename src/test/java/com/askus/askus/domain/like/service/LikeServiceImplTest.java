package com.askus.askus.domain.like.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.like.dto.LikeRequest;
import com.askus.askus.domain.like.dto.LikeResponse;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.config.TestConfig;

@Import({TestConfig.class})
@SpringBootTest
@Transactional
class LikeServiceImplTest {
	@Autowired
	private LikeService likeService;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private BoardRepository boardRepository;

	@Test
	void 좋아요_등록_성공() {
		// given
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		Board board = new Board(
			savedUsers,
			"title",
			"food name",
			Category.KOREAN,
			"ingredients",
			"content",
			"tag"
		);
		Board savedBoard = boardRepository.save(board);

		LikeRequest request = new LikeRequest(savedBoard.getId());

		// when
		LikeResponse response = likeService.addLike(savedUsers.getId(), request);

		// then
		assertThat(response.getBoardId()).isEqualTo(savedBoard.getId());
		assertThat(response.getLikeCount()).isEqualTo(1L);
	}

	@Test
	void 좋아요_삭제_성공() {
		// // given
		// Users users = new Users("email", "password", "nickname");
		// Users savedUsers = usersRepository.save(users);
		//
		// Board board = new Board(
		// 	savedUsers,
		// 	"title",
		// 	Category.KOREAN,
		// 	"ingredients",
		// 	"content",
		// 	"tag"
		// );
		// Board savedBoard = boardRepository.save(board);
		//
		// LikeRequest request = new LikeRequest(savedBoard.getId());
		// likeService.addLike(savedUsers.getId(), request);
		//
		// // when
		// LikeResponse response = likeService.deleteLike(savedUsers.getId(), request);
		//
		// // then
		// assertThat(response.getBoardId()).isEqualTo(savedBoard.getId());
		// assertThat(response.getLikeCount()).isEqualTo(0L);
	}
}
