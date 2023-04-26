package com.askus.askus.domain.users.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.domain.board.service.BoardService;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.image.service.ImageUploader;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;
import com.askus.askus.domain.users.repository.UsersRepository;
import com.askus.askus.domain.users.security.JwtTokenProvider;
import com.askus.askus.domain.users.security.SecurityUser;
import com.askus.askus.global.error.exception.AlreadyExistException;
import com.askus.askus.global.error.exception.MissMatchException;
import com.askus.askus.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final BoardService boardService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;
	private final ImageUploader imageUploader;

	@Transactional
	@Override
	public UsersResponse.SignUp signUp(UsersRequest.SignUp request) {

		if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new AlreadyExistException("email", request.getEmail());
		}

		if (!request.getPassword().equals(request.getCheckedPassword())) {
			throw new MissMatchException("checking password", request.getCheckedPassword());
		}

		// 회원정보 저장
		Users users = usersRepository.save(request.toEntity());

		String profileImageUrl = request.getProfileImage().uploadBy(imageUploader);
		ProfileImage profileImage = new ProfileImage(users, profileImageUrl);
		users.setProfileImage(profileImage);

		// 비밀번호 인코딩
		users.encodePassword(passwordEncoder);
		return new UsersResponse.SignUp(users.getEmail());
	}

	@Override
	public UsersResponse.DupEmail isDupEmail(String email) {
		return new UsersResponse.DupEmail(usersRepository.findByEmail(email).isPresent());
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
		UsersResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

		// 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
		redisTemplate.opsForValue()
			.set("RT:" + authentication.getName(),
				tokenInfo.getRefreshToken(),
				7,
				TimeUnit.DAYS);

		Users users = usersRepository.findByEmail(request.getEmail()).get();
		String email = users.getEmail();
		String nickname = users.getNickname();
		String profileImageUrl = users.getProfileImage().getUrl();

		return new UsersResponse.SignIn(email, nickname, profileImageUrl, tokenInfo);
	}

	public UsersResponse.TokenInfo reissue(UsersRequest.Reissue reissue) {
		// 1. Refresh Token 검증
		if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
			throw new MissMatchException("refresh token", reissue.getRefreshToken());
		}

		// 2. Access Token 에서 User email 을 가져온다.
		Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

		// 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져온다.
		String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());

		// (로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리)
		if (ObjectUtils.isEmpty(refreshToken)) {
			throw new NotFoundException("refresh token", refreshToken);
		}
		if (!refreshToken.equals(reissue.getRefreshToken())) {
			throw new MissMatchException("refresh token", reissue.getRefreshToken());
		}

		// 4. 새로운 토큰 생성
		UsersResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

		// 5. RefreshToken Redis 업데이트
		redisTemplate.opsForValue()
			.set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), 7, TimeUnit.DAYS);

		return tokenInfo;
	}

	@Override
	public UsersResponse.ProfileInfo getProfileInfo(String boardType, SecurityUser securityUser) {
		Long userId = securityUser.getId();
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));
		List<BoardResponse.Summary> boards = boardService.searchBoardsByType(boardType, userId);

		return UsersResponse.ProfileInfo.ofEntity(boards, users);
	}

	@Override
	@Transactional
	public UsersResponse.Patch updateUsers(long userId, UsersRequest.Patch request) {
		// 1. find users
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));

		// 2. update users & image
		request.update(users);
		String profileImageUrl = request.getProfileImage().uploadBy(imageUploader);
		ProfileImage profileImage = new ProfileImage(users, profileImageUrl);
		users.setProfileImage(profileImage);

		// 3. return
		return UsersResponse.Patch.ofEntity(users);
	}

	@Override
	@Transactional
	public void updatePassword(long userId, UsersRequest.PatchPassword request) {
		// 1. find users
		Users users = usersRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("users", userId));

		// 2. validate
		boolean matches = passwordEncoder.matches(request.getExistingPassword(), users.getPassword());
		if (!matches) {
			throw new MissMatchException("with existing password", request.getExistingPassword());
		}

		if (!request.getPassword().equals(request.getCheckedPassword())) {
			throw new MissMatchException("with checking password", request.getCheckedPassword());
		}

		// 3. update password
		request.update(users);
		users.encodePassword(passwordEncoder);
	}
}
