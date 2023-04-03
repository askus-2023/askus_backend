package com.askus.askus.domain.users.dto;

import com.askus.askus.domain.users.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String checkedPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    public Users toEntity() {
        return Users.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
