package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Getter
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
    private final String checkedPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private final String nickname;

    private Image profileImage;

    public SignUpRequest(
            String email,
            String password,
            String checkedPassword,
            String nickname,
            MultipartFile profileImage) {
        this.email = email;
        this.password = password;
        this.checkedPassword = password;
        this.nickname = nickname;
        setProfileImage(profileImage);
    }

    public void setProfileImage(MultipartFile profileImage) {
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
