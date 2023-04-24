package com.askus.askus.domain.users.service;

import com.askus.askus.domain.users.dto.UsersRequest;
import com.askus.askus.domain.users.dto.UsersResponse;
import com.askus.askus.domain.users.security.SecurityUser;

public interface UsersService {

	UsersResponse.SignUp signUp(UsersRequest.SignUp request) throws Exception;

	UsersResponse.SignIn signIn(UsersRequest.SignIn request);

	UsersResponse.DupEmail isDupEmail(String email);

	UsersResponse.TokenInfo reissue(UsersRequest.Reissue reissue);

	UsersResponse.ProfileInfo getProfileInfo(String boardType, SecurityUser securityUser);

	UsersResponse.Patch updateUsers(long userId, UsersRequest.Patch request);

	void updatePassword(long userId, UsersRequest.PatchPassword request);
}
