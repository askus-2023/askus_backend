package com.askus.askus.domain.reply.service;

import com.askus.askus.domain.reply.dto.ReplyAddRequest;
import com.askus.askus.domain.reply.dto.ReplyAddResponse;

public interface ReplyService {
	ReplyAddResponse addReply(long userId, long boardId, ReplyAddRequest request);
}
