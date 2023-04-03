package com.askus.askus.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SignUpResponse {

    private String email;
    private String nickname;
//    private String imageUrl;
}
