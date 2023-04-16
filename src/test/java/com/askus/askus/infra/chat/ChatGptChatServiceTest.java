package com.askus.askus.infra.chat;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.askus.askus.domain.chat.dto.ChatGptRequest;
import com.askus.askus.domain.chat.dto.ChatGptResponse;

@SpringBootTest
class ChatGptChatServiceTest {
	@Autowired
	private ChatGptChatService sut;

	@Test
	void 채팅_성공() {
		// given
		String question = "토마토로 만들 수 있는 음식 레시피 소개해줘";
		ChatGptRequest.Chat request = new ChatGptRequest.Chat(question);

		// when
		ChatGptResponse.Chat response = sut.chat(request);

		// then
		assertThat(response.getAnswer()).isNotNull();
	}
}
