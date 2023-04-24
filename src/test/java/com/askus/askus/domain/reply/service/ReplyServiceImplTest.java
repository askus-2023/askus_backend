package com.askus.askus.domain.reply.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.domain.Category;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;
import com.askus.askus.domain.reply.repository.ReplyRepository;
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
	@Autowired
	private ReplyRepository replyRepository;

	@Test
	void 댓글_등록_성공() {
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

		String content = "content";
		ReplyRequest request = new ReplyRequest(content);

		// when
		ReplyResponse response = replyService.addReply(savedUsers.getId(), savedBoard.getId(), request);

		// then
		assertThat(response.getReplyAuthor()).isEqualTo(savedUsers.getNickname());
		assertThat(response.getContent()).isEqualTo(content);
	}

	@Test
	void 댓글_수정_성공() {
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

		String content = "content";
		ReplyRequest request = new ReplyRequest(content);
		ReplyResponse response = replyService.addReply(savedUsers.getId(), savedBoard.getId(), request);

		List<Reply> findReplies = replyRepository.findAllByBoardAndDeletedAtNull(savedBoard);
		assertThat(findReplies.get(0).getContent()).isEqualTo(response.getContent());

		String updatedContent = "updated content";
		ReplyRequest updateRequest = new ReplyRequest(updatedContent);

		// when
		ReplyResponse updateResponse = replyService.updateReply(board.getId(), findReplies.get(0).getId(),
			updateRequest);
		List<Reply> updatedReplies = replyRepository.findAllByBoardAndDeletedAtNull(board);

		// then
		assertThat(updateResponse.getContent()).isEqualTo(updatedContent);
		assertThat(updatedReplies.get(0).getContent()).isEqualTo(updateResponse.getContent());
	}

	@Test
	void 댓글_삭제_성공() {
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

		String content = "content";
		ReplyRequest request = new ReplyRequest(content);

		ReplyResponse response = replyService.addReply(savedUsers.getId(), savedBoard.getId(), request);

		List<Reply> findReplies = replyRepository.findAllByBoardAndDeletedAtNull(savedBoard);
		assertThat(findReplies.get(0).getDeletedAt()).isNull();
		assertThat(savedBoard.getReplyCount()).isEqualTo(1L);

		// when
		replyService.deleteReply(savedBoard.getId(), findReplies.get(0).getId());
		List<Reply> deleteFindReplies = replyRepository.findAllByBoardAndDeletedAtNull(savedBoard);

		// then
		assertThat(deleteFindReplies.size()).isEqualTo(0);
	}
}
