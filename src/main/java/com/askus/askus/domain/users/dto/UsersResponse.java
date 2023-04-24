package com.askus.askus.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class UsersResponse {

	@Getter
	@AllArgsConstructor
	public static class SignUp {

		private String email;
	}

	@Getter
	public static class SignIn {

		private String email;
		private String nickname;
		private String imageUrl;
		private String accessToken;
		private String refreshToken;

		public SignIn(String email,
					  String nickname,
					  String imageUrl,
					  TokenInfo tokenInfo) {
			this.email = email;
			this.nickname = nickname;
			this.imageUrl = imageUrl;
			this.accessToken = tokenInfo.getAccessToken();
			this.refreshToken = tokenInfo.getRefreshToken();
		}
	}

	@Getter
	@AllArgsConstructor
	public static class DupEmail {
		private boolean duplicated;
	}

	@Getter
	@AllArgsConstructor
	public static class TokenInfo {

		private String grantType;
		private String accessToken;
		private String refreshToken;
	}
}
