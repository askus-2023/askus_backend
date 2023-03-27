package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.users.domain.Users;
import lombok.Getter;

@Getter
public class SignUpResponse {

    private final String email;
    private final String nickname;
    private final String imageUrl;

    public SignUpResponse(
            String email,
            String nickname,
            String imageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static SignUpResponse ofEntity(
            Users users,
            ProfileImage profileImage) {
        return new SignUpResponse(
                users.getEmail(),
                users.getNickname(),
                profileImage.getUrl()
        );
    }
}
