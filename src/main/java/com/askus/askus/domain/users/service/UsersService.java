package com.askus.askus.domain.users.service;

import com.askus.askus.domain.users.dto.*;

public interface UsersService {

    SignUpResponse signUp(SignUpRequest requestDto) throws Exception;
    SignInResponse signIn(SignInRequest requestDto);

    DupEmailResponse isDupEmail(String email);
}
