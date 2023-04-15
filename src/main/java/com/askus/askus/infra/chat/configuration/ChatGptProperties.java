package com.askus.askus.infra.chat.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@ConfigurationProperties(prefix = "chatgpt")
@ConstructorBinding
@Getter
public class ChatGptProperties {
	private final String apiKey;
	private final String model;
	private final int maxTokens;
	private final double temperature;
	private final double topP;

	public ChatGptProperties(String apiKey, String model, int maxTokens, double temperature, double topP) {
		this.apiKey = apiKey;
		this.model = model;
		this.maxTokens = maxTokens;
		this.temperature = temperature;
		this.topP = topP;
	}
}
