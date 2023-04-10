package com.askus.askus.domain.users.service;

import com.askus.askus.domain.users.dto.DupEmailResponse;
import com.askus.askus.domain.users.dto.SignInRequest;
import com.askus.askus.domain.users.dto.SignInResponse;
import com.askus.askus.domain.users.dto.SignUpRequest;
import com.askus.askus.domain.users.dto.SignUpResponse;

public interface UsersService {

	SignUpResponse signUp(SignUpRequest requestDto) throws Exception;

	SignInResponse signIn(SignInRequest requestDto);

	DupEmailResponse isDupEmail(String email);
}
