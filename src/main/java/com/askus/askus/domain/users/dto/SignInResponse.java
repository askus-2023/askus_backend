package com.askus.askus.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SignInResponse {

	private String email;
	private String accessToken;
	private String refreshToken;
}
