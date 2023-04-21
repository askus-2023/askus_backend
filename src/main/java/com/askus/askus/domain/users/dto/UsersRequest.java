package com.askus.askus.domain.users.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
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
				this.profileImage = new Image(ImageType.PROFILE, inputStream, "defaultProfileImage.png");
				return;
			}

			InputStream inputStream = getInputStream(profileImage);
			String originalFileName = getOriginalFileName(profileImage);
			this.profileImage = new Image(ImageType.PROFILE, inputStream, originalFileName);
		}

		private InputStream getInputStream(MultipartFile file) {
			ByteArrayInputStream byteArrayInputStream;
			try {
				byte[] byteArray = file.getBytes();
				byteArrayInputStream = new ByteArrayInputStream(byteArray);
			} catch (IOException e) {
				throw new KookleRuntimeException("이미지 파일 변환 실패", e);
			}
			return byteArrayInputStream;
		}

		private String getOriginalFileName(MultipartFile file) {
			return file.getOriginalFilename();
		}

		public Users toEntity() {
			return new Users(email, password, nickname);
		}
	}

	@Data
	@AllArgsConstructor
	@Builder
	public static class SignIn {
		private String email;
		private String password;
	}

	@Getter
	@AllArgsConstructor
	public static class DupEmail {
		private String email;
	}

	@Getter
	@AllArgsConstructor
	public static class Reissue {
		private String accessToken;
		private String refreshToken;
	}

	@Getter
	public static class Patch {
		private String email;
		private String nickname;
		private Image profileImage;

		public Patch(String email, String nickname, MultipartFile profileImage) {
			this.email = email;
			this.nickname = nickname;
			setProfileImage(profileImage);
		}

		public void setProfileImage(MultipartFile profileImage) {

			if (profileImage == null) {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultProfileImage.png");
				this.profileImage = new Image(ImageType.PROFILE, inputStream, "defaultProfileImage.png");
				return;
			}

			InputStream inputStream = getInputStream(profileImage);
			String originalFileName = getOriginalFileName(profileImage);
			this.profileImage = new Image(ImageType.PROFILE, inputStream, originalFileName);
		}

		private InputStream getInputStream(MultipartFile file) {
			ByteArrayInputStream byteArrayInputStream;
			try {
				byte[] byteArray = file.getBytes();
				byteArrayInputStream = new ByteArrayInputStream(byteArray);
			} catch (IOException e) {
				throw new KookleRuntimeException("이미지 파일 변환 실패", e);
			}
			return byteArrayInputStream;
		}

		private String getOriginalFileName(MultipartFile file) {
			return file.getOriginalFilename();
		}

		public void update(Users users) {
			users.update(this.email, this.nickname);
		}
	}

	@Getter
	@AllArgsConstructor
	public static class PatchPassword {
		private String existingPassword;
		private String password;
		private String checkedPassword;

		public void update(Users users) {
			users.updatePassword(this.password);
		}
	}
}
