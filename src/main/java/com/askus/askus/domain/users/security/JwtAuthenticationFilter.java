package com.askus.askus.domain.users.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;
	private final UsersRepository userRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		// 1. Request Header에서 JWT 토큰 추출
		String token = resolveToken((HttpServletRequest)request);

		// 2. validateToken으로 토큰 유효성 검사
		if (token != null && jwtTokenProvider.validationToken(token)) {
			// 토큰이 유효할 경우 토큰에서 authentication 객체(유저 정보)를 받아온다.
			Authentication authentication = jwtTokenProvider.getAuthentication(token);

			Users users = userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
			SecurityUser securityUser = new SecurityUser(users);

			Authentication customAuthentication = new UsernamePasswordAuthenticationToken(
				securityUser, null, securityUser.getAuthorities());

			// SecurityContext에 Authentication 객체를 저장한다.
			SecurityContextHolder.getContext().setAuthentication(customAuthentication);
		}
		chain.doFilter(request, response);
	}

	// Request Header에서 토큰 정보 추출
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
