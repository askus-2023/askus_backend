package com.askus.askus.infra.chat;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.chat.dto.ChatGptRequest;
import com.askus.askus.domain.chat.dto.ChatGptResponse;
import com.askus.askus.domain.chat.service.ChatService;
import com.askus.askus.global.properties.ChatGptProperties;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;

/**
 * Implementation for Chat Service using ChatGPT
 * @Function - chat
 * */
@Service
@RequiredArgsConstructor
public class ChatGptChatService implements ChatService {
	private final ChatgptService chatgptService;
	private final ChatGptProperties chatGptProperties;

	@Override
	public ChatGptResponse.Chat chat(ChatGptRequest.Chat request) {
		// get ready for request
		ChatRequest chatRequest = new ChatRequest(
			chatGptProperties.getModel(),
			request.getQuestion(),
			chatGptProperties.getMaxTokens(),
			chatGptProperties.getTemperature(),
			chatGptProperties.getTopP()
		);

		// send request
		ChatResponse chatResponse = chatgptService.sendChatRequest(chatRequest);

		// return response
		return ChatGptResponse.Chat.ofEntity(chatResponse);
	}
}
