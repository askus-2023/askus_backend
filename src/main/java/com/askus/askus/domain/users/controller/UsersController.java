package com.askus.askus.domain.users.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	@ApiResponse(responseCode = "400", description = "BAD REQUEST")
	@ApiResponse(responseCode = "404", description = "NOT FOUND")
	@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public UsersResponse.SignUp signUp(@Valid UsersRequest.SignUp request) throws Exception {
		return usersService.signUp(request);
	}

	@PostMapping("/signup/email/duplicated")
	@ResponseStatus(HttpStatus.OK)
	public UsersResponse.DupEmail checkDupEmail(UsersRequest.DupEmail request) {
		log.info("====={}=====", request.getEmail());
		return usersService.isDupEmail(request.getEmail());
	}

	@PostMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public UsersResponse.SignIn signIn(@Valid UsersRequest.SignIn request) {
		return usersService.signIn(request);
	}

	@PostMapping("/reissue")
	@ResponseStatus(HttpStatus.OK)
	public UsersResponse.TokenInfo reissue(UsersRequest.Reissue reissue) {
		return usersService.reissue(reissue);
	}

	@PatchMapping("/profiles")
	public UsersResponse.Patch updateUsers(
		@AuthenticationPrincipal SecurityUser securityUser,
		UsersRequest.Patch request
	) {
		return usersService.updateUsers(securityUser.getId(), request);
	}

	@PatchMapping("/profiles/password")
	public void updatePassword(
		@AuthenticationPrincipal SecurityUser securityUser,
		UsersRequest.PatchPassword request
	) {
		usersService.updatePassword(securityUser.getId(), request);
	}
}
