package com.askus.askus.domain.users.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UsersRequest {

	private static InputStream getInputStream(MultipartFile file) {
		ByteArrayInputStream byteArrayInputStream;
		try {
			byte[] byteArray = file.getBytes();
			byteArrayInputStream = new ByteArrayInputStream(byteArray);
		} catch (IOException e) {
			throw new KookleRuntimeException("이미지 파일 변환 실패", e);
		}
		return byteArrayInputStream;
	}

	private static String getOriginalFileName(MultipartFile file) {
		return file.getOriginalFilename();
	}


	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SignUp {

		@Schema(description = "이메일", example = "email@email.com")
		private String email;
		@Schema(description = "비밀번호", example = "password")
		private String password;
		@Schema(description = "비밀번호 검증", example = "password")
		private String checkedPassword;
		@Schema(description = "닉네임", example = "쿠킹마마")
		private String nickname;
		@Schema(description = "프로필 이미지(File)", example = "profileImage.png")
		private Image profileImage;

		public SignUp(String email, String password, String checkedPassword, String nickname,
			MultipartFile profileImage) {
			this.email = email;
			this.password = password;
			this.checkedPassword = checkedPassword;
			this.nickname = nickname;
			setProfileImage(profileImage);
		}

		public void setProfileImage(MultipartFile profileImage) {
			if (profileImage == null) {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultProfileImage.png");
				this.profileImage = new Image(inputStream, "defaultProfileImage.png");
				return;
			}
			InputStream inputStream = getInputStream(profileImage);
			String originalFileName = getOriginalFileName(profileImage);
			this.profileImage = new Image(inputStream, originalFileName);
		}

		public Users toEntity() {
			return new Users(email, password, nickname);
		}
	}

	@Data
	@AllArgsConstructor
	@Builder
	public static class SignIn {
		@Schema(description = "이메일", example = "email@email.com")
		private String email;
		@Schema(description = "비밀번호", example = "password")
		private String password;
	}

	@Getter
	@AllArgsConstructor
	public static class DupEmail {
		@Schema(description = "이메일", example = "email@email.com")
		private String email;
	}

	@Getter
	@AllArgsConstructor
	public static class Reissue {
		@Schema(description = "엑세스 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String accessToken;
		@Schema(description = "리프레시 토큰(jwt)", example = "2&836dsag218#$%@$~")
		private String refreshToken;
	}

	@Getter
	public static class Patch {
		@Schema(description = "이메일", example = "email@email.com")
		private String email;
		@Schema(description = "닉네임", example = "쿠킹마마")
		private String nickname;
		@Schema(description = "프로필 이미지", example = "profileImage.png")
		private Image profileImage;

		public Patch(String email, String nickname, MultipartFile profileImage) {
			this.email = email;
			this.nickname = nickname;
			setProfileImage(profileImage);
		}

		public void setProfileImage(MultipartFile profileImage) {
			if (profileImage == null || profileImage.isEmpty()) {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultProfileImage.png");
				this.profileImage = new Image(inputStream, "defaultProfileImage.png");
				return;
			}
			InputStream inputStream = getInputStream(profileImage);
			String originalFileName = getOriginalFileName(profileImage);
			this.profileImage = new Image(inputStream, originalFileName);
		}

		public void update(Users users) {
			users.update(this.email, this.nickname);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class PatchPassword {
		@Schema(description = "기존 비밀번호", example = "existingPassword")
		private String existingPassword;
		@Schema(description = "변경할 비밀번호", example = "password")
		private String password;
		@Schema(description = "변경할 비밀번호 확인", example = "password")
		private String checkedPassword;

		public void update(Users users) {
			users.updatePassword(this.password);
		}
	}
}
