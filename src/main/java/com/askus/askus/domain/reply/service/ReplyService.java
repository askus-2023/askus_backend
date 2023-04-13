package com.askus.askus.domain.reply.service;

import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;

public interface ReplyService {
	ReplyResponse.Post addReply(long userId, long boardId, ReplyRequest.Post request);

	ReplyResponse.Patch updateReply(long boardId, long replyId, ReplyRequest.Patch request);

	ReplyResponse.Delete deleteReply(long boardId, long replyId);
}
