package com.askus.askus.domain.reply.service;

import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;

public interface ReplyService {
	ReplyResponse addReply(long userId, long boardId, ReplyRequest request);

	ReplyResponse updateReply(long boardId, long replyId, ReplyRequest request);

	void deleteReply(long boardId, long replyId);
}
