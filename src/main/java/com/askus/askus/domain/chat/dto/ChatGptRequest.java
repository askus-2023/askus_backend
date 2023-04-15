package com.askus.askus.domain.chat.dto;

import lombok.Getter;

public class ChatGptRequest {
	@Getter
	public static class Chat {
		private String question;

		public Chat() {
		}

		public Chat(String question) {
			this.question = "안녕, ChatGPT! 나 질문이 있어." + question;
		}
	}
}
