package com.askus.askus.domain.reply.service;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.repository.BoardRepository;
import com.askus.askus.domain.reply.domain.Reply;
import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;
import com.askus.askus.domain.reply.repository.ReplyRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	private final ReplyRepository replyRepository;
	private final UsersRepository usersRepository;
	private final BoardRepository boardRepository;

	@Override
	public ReplyResponse.Post addReply(long userId, long boardId, ReplyRequest.Post request) {
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new KookleRuntimeException("User Not found"));

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		Reply reply = replyRepository.save(request.toEntity(users, board));

		board.addReplyCount();
		boardRepository.save(board);

		return ReplyResponse.Post.ofEntity(board, reply);
	}

	@Override
	public ReplyResponse.Patch updateReply(long boardId, long replyId, ReplyRequest.Patch request) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new KookleRuntimeException("Reply Not Found"));

		request.update(reply);
		Reply savedReply = replyRepository.save(reply);

		return ReplyResponse.Patch.ofEntity(board.getUsers(), savedReply);
	}

	@Override
	public ReplyResponse.Delete deleteReply(long boardId, long replyId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new KookleRuntimeException("Board Not Found"));

		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new KookleRuntimeException("Reply Not Found"));

		reply.delete();
		board.deleteReplyCount();

		Board savedBoard = boardRepository.save(board);
		replyRepository.save(reply);

		return ReplyResponse.Delete.ofEntity(savedBoard);
	}
}
