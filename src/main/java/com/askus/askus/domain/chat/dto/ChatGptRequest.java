package com.askus.askus.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Request DTO for domain Chat
 * */
public class ChatGptRequest {
	@Getter
	public static class Chat {
		@Schema(description = "질문", example = "토마토로 만들 수 있는 음식 레시피 소개해줘.")
		private String question;

		public Chat() {
		}

		public Chat(String question) {
			this.question = "안녕, ChatGPT! 나 질문이 있어." + question;
		}
	}
}
