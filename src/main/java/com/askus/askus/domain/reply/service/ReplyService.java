package com.askus.askus.domain.reply.service;

import java.util.List;

import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;

public interface ReplyService {
	/**
	 * add reply by given request(ReplyRequest)
	 *
	 * @param userId - current userId
	 * @param boardId - selected boardId
	 * @param request - ReplyRequest
	 * @return - added reply data(ReplyResponse)
	 */
	ReplyResponse addReply(long userId, long boardId, ReplyRequest request);

	List<ReplyResponse> searchReplies(long userId, long boardId);

	/**
	 * update reply by given request(ReplyRequest)
	 *
	 * @param boardId - selected boardId
	 * @Param replyId - selected replyId
	 * @param request - ReplyRequest
	 * @return - updated reply data(ReplyResponse)
	 */
	ReplyResponse updateReply(long boardId, long replyId, ReplyRequest request);

	/**
	 * delete reply by given request(replyId)
	 *
	 * @param boardId - selected boardId
	 * @Param replyId - selected replyId
	 */
	void deleteReply(long boardId, long replyId);
}
