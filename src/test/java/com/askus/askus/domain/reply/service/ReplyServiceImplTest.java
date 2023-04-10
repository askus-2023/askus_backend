package com.askus.askus.domain.reply.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.reply.dto.ReplyAddRequest;
import com.askus.askus.domain.reply.dto.ReplyAddResponse;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;

@SpringBootTest
@Transactional
class ReplyServiceImplTest {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private BoardRepository boardRepository;

	@Test
	void 댓글_등록_성공() {
		// given
		Users users = new Users("email", "password", "nickname");
		Users savedUsers = usersRepository.save(users);

		Board board = new Board(
			savedUsers,
			"title",
			Category.KOREAN,
			"ingredients",
			"content",
			"tag"
		);
		Board savedBoard = boardRepository.save(board);

		String content = "content";
		ReplyAddRequest request = new ReplyAddRequest(content);

		// when
		ReplyAddResponse response = replyService.addReply(savedUsers.getId(), savedBoard.getId(), request);

		// then
		assertThat(response.getBoardId()).isEqualTo(savedBoard.getId());
		assertThat(response.getReplyCount()).isEqualTo(1L);
		assertThat(response.getContent()).isEqualTo(content);
	}
}
