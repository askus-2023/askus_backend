package com.askus.askus.domain.users.controller;

import com.askus.askus.domain.users.dto.*;
import com.askus.askus.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signUp(
            @RequestPart("signUpRequest") SignUpRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
            ) throws Exception {
        request.setProfileImage(profileImage);
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
}
