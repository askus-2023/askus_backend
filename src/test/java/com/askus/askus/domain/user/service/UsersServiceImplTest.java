package com.askus.askus.domain.user.service;

import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.SignUpRequest;
import com.askus.askus.domain.users.dto.SignUpResponse;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.domain.users.service.UsersServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersServiceImpl userService;

    @Test
    void 기본_회원가입(){
        // given
        SignUpRequest requestDto = SignUpRequest.builder()
                .nickname("nickname")
                .password("password")
                .checkedPassword("password")
                .email("email")
                .build();
        Users users = requestDto.toEntity();
        given(usersRepository.save(any(Users.class))).willReturn(users);

        // when
        try {
            SignUpResponse responseDto = userService.signUp(requestDto);

            // then
            Assertions.assertThat(responseDto.getEmail()).isEqualTo(requestDto.getEmail());
            Assertions.assertThat(responseDto.getNickname()).isEqualTo(requestDto.getNickname());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void 중복_회원가입() {
        // given
        SignUpRequest requestDto = SignUpRequest.builder()
                .nickname("nickname")
                .password("password")
                .checkedPassword("password")
                .email("email")
                .build();
        given(usersRepository.findByEmail("email")).willReturn(Optional.of(Users.builder()
                .nickname("nickname")
                .password("password")
                .email("email")
                .build()));

        // when
        Assertions.assertThatThrownBy(() -> userService.signUp(requestDto))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("이메일");
    }

    @Test
    void 가입시_비밀번호_다름() {
        // given
        SignUpRequest requestDto = SignUpRequest.builder()
                .nickname("nickname")
                .password("password")
                .checkedPassword("password123")
                .email("email")
                .build();

        // when
        Assertions.assertThatThrownBy(() -> userService.signUp(requestDto))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("비밀번호");
    }
}