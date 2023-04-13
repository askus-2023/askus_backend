package com.askus.askus.domain.reply.service;

import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;

public interface ReplyService {
	ReplyResponse.Post addReply(long userId, long boardId, ReplyRequest.Post request);
}
