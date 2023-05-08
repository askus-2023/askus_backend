package com.askus.askus.domain.chat.service;

import com.askus.askus.domain.chat.dto.ChatGptRequest;
import com.askus.askus.domain.chat.dto.ChatGptResponse;

public interface ChatService {

	/**
	 * chat with openAi by given request(ChatGptRequest.Chat)
	 *
	 * @param request - ChatGptRequest.Chat
	 * @return - answer from openAi(ChatGptResponse.Chat)
	 */
	ChatGptResponse.Chat chat(ChatGptRequest.Chat request);
}
