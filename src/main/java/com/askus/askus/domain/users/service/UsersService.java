package com.askus.askus.domain.users.service;

import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;

public interface UsersService {

	UsersResponse.SignUp signUp(UsersRequest.SignUp request) throws Exception;

	UsersResponse.SignIn signIn(UsersRequest.SignIn request);

	UsersResponse.DupEmail isDupEmail(String email);
}
