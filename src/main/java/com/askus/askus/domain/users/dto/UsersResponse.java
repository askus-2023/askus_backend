package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.users.domain.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class UsersResponse {
	@Getter
	public static class SignUp {
		@Schema(description = "이메일", example = "email@email.com")
		private final String email;
		@Schema(description = "닉네임", example = "쿠킹마마")
		private final String nickname;
		@Schema(description = "프로필 이미지 주소", example = "http://profile/image/url")
		private final String imageUrl;

		public SignUp(
			String email,
			String nickname,
			String imageUrl) {
			this.email = email;
			this.nickname = nickname;
			this.imageUrl = imageUrl;
		}

		public static UsersResponse.SignUp ofEntity(Users users) {
			return new UsersResponse.SignUp(
				users.getEmail(),
				users.getNickname(),
				users.getProfileImage().getUrl()
			);
		}
	}

	@Builder
	@Data
	@AllArgsConstructor
	public static class SignIn {
		@Schema(description = "이메일", example = "email@email.com")
		private String email;
		@Schema(description = "엑세스 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String accessToken;
		@Schema(description = "리프레시 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String refreshToken;
	}

	@Builder
	@Data
	@AllArgsConstructor
	public static class DupEmail {
		@Schema(description = "이메일 중복 여부", example = "true/false")
		private boolean duplicated;
	}

	@Builder
	@Getter
	public static class TokenInfo {
		@Schema(description = "bearer", example = "bearer")
		private String grantType;
		@Schema(description = "엑세스 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String accessToken;
		@Schema(description = "리프레시 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String refreshToken;
	}

	@Getter
	@AllArgsConstructor
	public static class Patch {
		@Schema(description = "이메일", example = "email@email.com")
		private final String email;
		@Schema(description = "닉네임", example = "쿠킹마마")
		private final String nickname;
		@Schema(description = "프로필 이미지 주소", example = "http://profile/image/url")
		private final String profileImageUrl;

		public static Patch ofEntity(Users users) {
			return new Patch(users.getEmail(), users.getNickname(), users.getProfileImage().getUrl());
		}
	}
}
