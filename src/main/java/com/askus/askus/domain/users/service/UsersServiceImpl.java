package com.askus.askus.domain.users.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.image.service.ImageService;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.DupEmailResponse;
import com.askus.askus.domain.users.dto.SignInRequest;
import com.askus.askus.domain.users.dto.SignInResponse;
import com.askus.askus.domain.users.dto.SignUpRequest;
import com.askus.askus.domain.users.dto.SignUpResponse;
import com.askus.askus.domain.users.dto.TokenInfo;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.domain.users.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final ImageService imageService;

	@Transactional
	@Override
	public SignUpResponse signUp(SignUpRequest request) throws Exception {

		if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
			log.error("중복된 이메일로 가입 시도: {}", request.getEmail());
			throw new Exception("이미 존재하는 이메일입니다.");
		}

		if (!request.getPassword().equals(request.getCheckedPassword())) {
			log.error("가입 시 비밀번호 불일치: {}", request.getEmail());
			throw new Exception("비밀번호가 일치하지 않습니다.");
		}

		Users users = usersRepository.save(request.toEntity());
		ProfileImage profileImage = imageService.uploadProfileImage(users, request);

		// 비밀번호 인코딩
		users.encodePassword(passwordEncoder);
		return SignUpResponse.ofEntity(users, profileImage);
	}

	@Override
	public DupEmailResponse isDupEmail(String email) {
		return DupEmailResponse.builder()
			.duplicated(usersRepository.findByEmail(email).isPresent())
			.build();
	}

	@Transactional
	public SignInResponse signIn(SignInRequest requestDto) {
		// 1. Login Email/PW를 기반으로 Authentication 객체 생성
		// 이때 authentication는 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

		// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 메서드가 실행될 때 customerUserDetailsService에서 만든 loadUserByUsername 메서드가 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

		// TODO:: RefreshToken Redis 저장

		return SignInResponse.builder()
			.email(authentication.getName())
			.accessToken(tokenInfo.getAccessToken())
			.refreshToken(tokenInfo.getRefreshToken())
			.build();
	}
}
