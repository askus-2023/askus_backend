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

	/**
	 * update users email,nickname by given request(UsersRequest.Patch)
	 *
	 * @param userId - current userId
	 * @param request - UsersRequest.Patch
	 * @return - updated users data(UsersResponse.Patch)
	 */
	UsersResponse.Patch updateUsers(long userId, UsersRequest.Patch request);

	/**
	 * update users password by given request(UsersRequest.PatchPassword)
	 *
	 * @param userId - current userId
	 * @param request - UsersRequest.PatchPassword
	 */
	void updatePassword(long userId, UsersRequest.PatchPassword request);

	/**
	 * logout user
	 *
	 * @Param securityUser - current user
	 * @Param accessToken - current user access token
	 */
	void logout(SecurityUser securityUser, String accessToken);
}
