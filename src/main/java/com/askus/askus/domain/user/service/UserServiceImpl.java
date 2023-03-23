package com.askus.askus.domain.user.service;

import com.askus.askus.domain.user.domain.User;
import com.askus.askus.domain.user.dto.*;
import com.askus.askus.domain.user.repository.UserRepository;
import com.askus.askus.domain.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public UserSignUpResponseDto signUp(UserSignUpRequestDto requestDto) throws Exception {

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            log.error("중복된 이메일로 가입 시도: {}", requestDto.getEmail());
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getCheckedPassword())){
            log.error("가입 시 비밀번호 재확인 실패: {}", requestDto.getEmail());
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.save(requestDto.toEntity());
        // 비밀번호 인코딩
        user.encodePassword(passwordEncoder);

        return UserSignUpResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
