package com.askus.askus.domain.chat.dto;

import io.github.flashvayne.chatgpt.dto.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChatGptResponse {
	@Getter
	@AllArgsConstructor
	public static class Chat {
		private final String answer;

		public static Chat ofEntity(ChatResponse response) {
			String answer = response.getChoices().get(0).getText();
			return new Chat(answer);
		}
	}
}
