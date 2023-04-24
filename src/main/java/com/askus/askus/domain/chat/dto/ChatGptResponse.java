package com.askus.askus.domain.chat.dto;

import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ChatGptResponse {
	@Getter
	@AllArgsConstructor
	public static class Chat {
		@Schema(description = "대답", example = "돼지고기와 김치를 넣고 끓이세요.")
		private final String answer;

		public static Chat ofEntity(ChatResponse response) {
			String answer = response.getChoices().get(0).getText();
			return new Chat(answer);
		}
	}
}
