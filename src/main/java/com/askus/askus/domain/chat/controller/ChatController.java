package com.askus.askus.domain.chat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.chat.dto.ChatGptRequest;
import com.askus.askus.domain.chat.dto.ChatGptResponse;
import com.askus.askus.domain.chat.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/chats")
@RequiredArgsConstructor
public class ChatController {
	private final ChatService chatService;

	@Operation(
		summary = "채팅",
		description = "OpenAi에 질의응답 합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ChatGptResponse.Chat.class)))
	@PostMapping
	public ChatGptResponse.Chat chat(
		@RequestBody ChatGptRequest.Chat request
	) {
		return chatService.chat(request);
	}
}
