package com.askus.askus.domain.reply.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;
import com.askus.askus.domain.reply.repository.ReplyRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
	private final ReplyRepository replyRepository;
	private final UsersRepository usersRepository;
	private final BoardRepository boardRepository;

	@Override
	@Transactional
	public ReplyResponse addReply(long userId, long boardId, ReplyRequest request) {
		// 1. find users
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));

		// 2. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("board", boardId));

		// 3. save & update board
		Reply reply = replyRepository.save(request.toEntity(users, board));
		board.addReplyCount();

		// 4. return
		return ReplyResponse.ofEntity(users, reply);
	}

	@Override
	public List<ReplyResponse> searchReplies(long userId, long boardId) {
		// 1. find users
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));

		// 2. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("board", boardId));

		// 2. find replies & create response & return
		return replyRepository.findAllByBoardAndDeletedAtNull(board).stream()
			.map(reply -> ReplyResponse.ofEntity(users, reply))
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ReplyResponse updateReply(long boardId, long replyId, ReplyRequest request) {
		// 1. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("board", boardId));

		// 2. find reply
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new NotFoundException("reply", replyId));

		// 3. update
		request.update(reply);

		// 4. return
		return ReplyResponse.ofEntity(reply.getUsers(), reply);
	}

	@Override
	@Transactional
	public void deleteReply(long boardId, long replyId) {
		// 1. find board
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new NotFoundException("board", boardId));

		// 2. find reply
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new NotFoundException("reply", replyId));

		// 3. update
		reply.delete();
		board.deleteReplyCount();
	}
}
