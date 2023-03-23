package com.askus.askus.domain.user.service;

import com.askus.askus.domain.user.dto.UserSignInRequestDto;
import com.askus.askus.domain.user.dto.UserSignInResponseDto;
import com.askus.askus.domain.user.dto.UserSignUpRequestDto;
import com.askus.askus.domain.user.dto.UserSignUpResponseDto;

public interface UserService {

    public UserSignUpResponseDto signUp(UserSignUpRequestDto requestDto) throws Exception;
}
