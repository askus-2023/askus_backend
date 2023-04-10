package com.askus.askus.domain.users.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.users.dto.DupEmailRequest;
import com.askus.askus.domain.users.dto.DupEmailResponse;
import com.askus.askus.domain.users.dto.SignInRequest;
import com.askus.askus.domain.users.dto.SignInResponse;
import com.askus.askus.domain.users.dto.SignUpRequest;
import com.askus.askus.domain.users.dto.SignUpResponse;
import com.askus.askus.domain.users.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UsersController {

	private final UsersService usersService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public SignUpResponse signUp(@Valid SignUpRequest request) throws Exception {
		return usersService.signUp(request);
	}

	@PostMapping("/signup/email/duplicated")
	@ResponseStatus(HttpStatus.OK)
	public DupEmailResponse checkDupEmail(DupEmailRequest request) {
		return usersService.isDupEmail(request.getEmail());
	}

	@PostMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public SignInResponse signIn(@Valid SignInRequest request) {
		return usersService.signIn(request);
	}
}
