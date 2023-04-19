package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.users.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class UsersResponse {
	@Getter
	public static class SignUp {

		private final String email;
		private final String nickname;
		private final String imageUrl;

		public SignUp(
			String email,
			String nickname,
			String imageUrl) {
			this.email = email;
			this.nickname = nickname;
			this.imageUrl = imageUrl;
		}

		public static UsersResponse.SignUp ofEntity(
			Users users,
			ProfileImage profileImage) {
			String imageUrl = (profileImage != null) ? profileImage.getUrl() : null;

			return new UsersResponse.SignUp(
				users.getEmail(),
				users.getNickname(),
				imageUrl
			);
		}
	}

	@Builder
	@Data
	@AllArgsConstructor
	public static class SignIn {

		private String email;
		private String accessToken;
		private String refreshToken;
	}

	@Builder
	@Data
	@AllArgsConstructor
	public static class DupEmail {
		private boolean duplicated;
	}

	@Builder
	@Getter
	public static class TokenInfo {

		private String grantType;
		private String accessToken;
		private String refreshToken;
	}

	@Getter
	@AllArgsConstructor
	public static class Patch {
		private final String email;
		private final String nickname;
		private final String profileImageUrl;

		public static Patch ofEntity(Users users, ProfileImage profileImage) {
			return new Patch(users.getEmail(), users.getNickname(), profileImage.getUrl());
		}
	}
}
