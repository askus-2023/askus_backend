package com.askus.askus.domain.users.controller;

import com.askus.askus.domain.users.dto.*;
import com.askus.askus.domain.users.security.SecurityUser;
import com.askus.askus.domain.users.service.UsersService;
import com.askus.askus.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UsersController {

    private final UsersService usersService;

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
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest request) throws Exception {
        return usersService.signUp(request);
    }

    @PostMapping("/signup/email/duplicated")
    @ResponseStatus(HttpStatus.OK)
    public DupEmailResponse checkDupEmail(@RequestBody DupEmailRequest request) {
        return usersService.isDupEmail(request.getEmail());
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(@Valid @RequestBody SignInRequest request) {
        return usersService.signIn(request);
    }

    @GetMapping("/test")
    public String check(@AuthenticationPrincipal SecurityUser securityUser) {
        return securityUser.getEmail();
    }
}