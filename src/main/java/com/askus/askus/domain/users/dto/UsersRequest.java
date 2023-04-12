package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UsersRequest {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUp {

        private String email;
        private String password;
        private String checkedPassword;
        private String nickname;
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

            if (profileImage == null || profileImage.isEmpty()) {
                this.profileImage = null;
                return;
            }
            ;

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
}
