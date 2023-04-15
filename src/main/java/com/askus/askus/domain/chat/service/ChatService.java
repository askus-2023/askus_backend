package com.askus.askus.domain.chat.service;

import com.askus.askus.domain.chat.dto.ChatGptRequest;
import com.askus.askus.domain.chat.dto.ChatGptResponse;

public interface ChatService {
	ChatGptResponse.Chat chat(ChatGptRequest.Chat request);
}
