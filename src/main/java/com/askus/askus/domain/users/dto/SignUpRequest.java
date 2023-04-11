package com.askus.askus.domain.users.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

	private String email;
	private String password;
	private String checkedPassword;
	private String nickname;
	private Image profileImage;

	public SignUpRequest(
			String email,
			String password,
			String checkedPassword,
			String nickname,
			MultipartFile profileImage
	) {
		this.email = email;
		this.password = password;
		this.checkedPassword = checkedPassword;
		this.nickname = nickname;
		setProfileImage(profileImage);
	}

	public void setProfileImage(MultipartFile profileImage) {

		if(profileImage==null) {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultProfileImage.webp");
			this.profileImage = new Image(ImageType.PROFILE, inputStream, "defaultProfileImage.webp");
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
