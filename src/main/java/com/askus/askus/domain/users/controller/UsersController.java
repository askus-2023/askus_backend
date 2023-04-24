package com.askus.askus.domain.users.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;
import com.askus.askus.domain.users.security.SecurityUser;
import com.askus.askus.domain.users.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UsersController {

	private final UsersService usersService;

	@Operation(
		summary = "회원가입",
		description = "이메일, 비밀번호, 닉네임, 프로필 이미지(선택)을 이용한 회원 가입입니다."
	)
	@ApiResponse(responseCode = "201", description = "created", content = @Content(schema = @Schema(implementation = UsersResponse.SignUp.class)))
	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public UsersResponse.SignUp signUp(@Valid UsersRequest.SignUp request) throws Exception {
		return usersService.signUp(request);
	}

	@Operation(
		summary = "이메일 중복 확인",
		description = "이메일을 이용한 중복 확인입니다."
	)
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UsersResponse.DupEmail.class)))
	@PostMapping("/signup/email/duplicated")
	@ResponseStatus(HttpStatus.OK)
	public UsersResponse.DupEmail checkDupEmail(UsersRequest.DupEmail request) {
		log.info("====={}=====", request.getEmail());
		return usersService.isDupEmail(request.getEmail());
	}

	@Operation(
		summary = "로그인",
		description = "이메일, 비밀번호를 이용한 로그인 입니다."
	)
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UsersResponse.SignIn.class)))
	@PostMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public UsersResponse.SignIn signIn(@Valid UsersRequest.SignIn request) {
		return usersService.signIn(request);
	}

	@Operation(
		summary = "리프레시 토큰 재발행",
		description = "엑세스 토큰, 리프레시 토큰을 사용해 리프레시 토큰을 재발급 합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UsersResponse.TokenInfo.class)))
	@PostMapping("/reissue")
	@ResponseStatus(HttpStatus.OK)
	public UsersResponse.TokenInfo reissue(UsersRequest.Reissue reissue) {
		return usersService.reissue(reissue);
	}

	@Operation(
		summary = "프로필 수정",
		description = "현재 로그인된 사용자의 프로필 정보를 수정한 후, 로그인 페이지로 리다이렉트 시킵니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UsersResponse.Patch.class)))
	@PatchMapping("/profiles")
	public UsersResponse.Patch updateUsers(
		@AuthenticationPrincipal SecurityUser securityUser,
		UsersRequest.Patch request
	) {
		return usersService.updateUsers(securityUser.getId(), request);
	}

	@Operation(
		summary = "비밀번호 수정",
		description = "현재 로그인된 사용자의 비밀번호를 수정한 후, 로그인 페이지로 리다이렉트 시킵니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@PatchMapping("/profiles/password")
	public void updatePassword(
		@AuthenticationPrincipal SecurityUser securityUser,
		@RequestBody UsersRequest.PatchPassword request
	) {
		usersService.updatePassword(securityUser.getId(), request);
	}
}
