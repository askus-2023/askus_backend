package com.askus.askus.domain.users.dto;

import java.util.List;

import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.domain.users.domain.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response DTO for domain Users
 * */
public class UsersResponse {

	@Getter
	@AllArgsConstructor
	public static class SignUp {
		@Schema(description = "이메일", example = "email@email.com")
		private String email;
	}

	@Getter
	@AllArgsConstructor
	public static class SignIn {
		@Schema(description = "이메일", example = "email@email.com")
		private final String email;
		@Schema(description = "닉네임", example = "쿠킹마마")
		private final String nickname;
		@Schema(description = "프로필 이미지 주소", example = "http://profile/image/url")
		private final String imageUrl;
		@Schema(description = "엑세스 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String accessToken;
		@Schema(description = "리프레시 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String refreshToken;

		public static UsersResponse.SignIn ofEntity(
			Users users,
			TokenInfo tokenInfo
		) {
			return new UsersResponse.SignIn(
				users.getEmail(),
				users.getNickname(),
				users.getProfileImage().getUrl(),
				tokenInfo.getAccessToken(),
				tokenInfo.getRefreshToken());
		}
	}

	@Getter
	@AllArgsConstructor
	public static class DupEmail {
		@Schema(description = "이메일 중복 여부", example = "true/false")
		private boolean duplicated;
	}

	@Getter
	@AllArgsConstructor
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
	public static class ProfileInfo {
		@Schema(description = "글 목록", example = "[대충 보드 리스트,,,]")
		List<BoardResponse.Summary> boards;
		@Schema(description = "이메일", example = "cookle@cookle.com")
		private String email;
		@Schema(description = "닉네임", example = "쿠킹마마")
		private String nickname;
		@Schema(description = "프로필 이미지 주소", example = "http://profile/image/url")
		private String profileImageUrl;

		public static UsersResponse.ProfileInfo ofEntity(List<BoardResponse.Summary> boards, Users users) {
			return new UsersResponse.ProfileInfo(boards, users.getEmail(), users.getNickname(),
				users.getProfileImage().getUrl());
		}
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
