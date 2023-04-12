package com.askus.askus.domain.users.service;

import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.image.service.ImageService;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.TokenInfo;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.domain.users.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

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
	private final RedisTemplate redisTemplate;

	@Transactional
	@Override
	public UsersResponse.SignUp signUp(UsersRequest.SignUp request) throws Exception {

		if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
			log.error("중복된 이메일로 가입 시도: {}", request.getEmail());
			throw new Exception("이미 존재하는 이메일입니다.");
		}

		if (!request.getPassword().equals(request.getCheckedPassword())) {
			log.error("가입 시 비밀번호 불일치: {}", request.getEmail());
			throw new Exception("비밀번호가 일치하지 않습니다.");
		}

		Users users = usersRepository.save(request.toEntity());
		ProfileImage profileImage = null;
		if (request.getProfileImage() != null) {
			profileImage = imageService.uploadProfileImage(users, request);
		}

		// 비밀번호 인코딩
		users.encodePassword(passwordEncoder);
		return UsersResponse.SignUp.ofEntity(users, profileImage);
	}

	@Override
	public UsersResponse.DupEmail isDupEmail(String email) {
		return UsersResponse.DupEmail.builder()
			.duplicated(usersRepository.findByEmail(email).isPresent())
			.build();
	}

	@Transactional
	public UsersResponse.SignIn signIn(UsersRequest.SignIn request) {
		// 1. Login Email/PW를 기반으로 Authentication 객체 생성
		// 이때 authentication는 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

		// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 메서드가 실행될 때 customerUserDetailService에서 만든 loadUserByUsername 메서드가 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

		// 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
		redisTemplate.opsForValue()
				.set("RT:" + authentication.getName(),
						tokenInfo.getRefreshToken(),
						7,
						TimeUnit.DAYS);

		return UsersResponse.SignIn.builder()
			.email(authentication.getName())
			.accessToken(tokenInfo.getAccessToken())
			.refreshToken(tokenInfo.getRefreshToken())
			.build();
	}
}
