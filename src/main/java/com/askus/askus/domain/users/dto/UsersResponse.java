package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.board.dto.BoardResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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

	@AllArgsConstructor
	public static class profileInfo {
		private String nickname;
		private String profileImageUrl;
		List<BoardResponse.Summary> boards;
	}
}
