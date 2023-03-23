package com.askus.askus.domain.user.controller;

import com.askus.askus.domain.user.dto.*;
import com.askus.askus.domain.user.service.UserService;
import com.askus.askus.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionHandler(Exception e) {
        return ErrorResponse.builder()
                .title("Error")
                .status("400")
                .detail(e.getMessage())
                .build();
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignUpResponseDto signUp(@Valid @RequestBody UserSignUpRequestDto requestDto) throws Exception {
        return userService.signUp(requestDto);
    }
}
