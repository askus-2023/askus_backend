package com.askus.askus.domain.chat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.chat.dto.ChatGptRequest;
import com.askus.askus.domain.chat.dto.ChatGptResponse;
import com.askus.askus.domain.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/chats")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@PostMapping
	public ChatGptResponse.Chat chat(
		@RequestBody ChatGptRequest.Chat request
	) {
		return chatService.chat(request);
	}
}
