package com.askus.askus.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserSignUpResponseDto {

    private String email;
    private String nickname;
//    private String imageUrl;
}
