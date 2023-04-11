package com.askus.askus.domain.like.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.like.dto.LikeRequest;
import com.askus.askus.domain.like.dto.LikeResponse;
import com.askus.askus.domain.like.service.LikeService;
import com.askus.askus.domain.users.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/likes")
@RequiredArgsConstructor
public class LikeController {
	private final LikeService likeService;

	@PostMapping
	public ResponseEntity<LikeResponse> addLike(
		@AuthenticationPrincipal SecurityUser securityUser,
		LikeRequest request
	) {
		LikeResponse response = likeService.addLike(securityUser.getId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	@DeleteMapping
	public ResponseEntity<LikeResponse> deleteLike(
		@AuthenticationPrincipal SecurityUser securityUser,
		LikeRequest request
	) {
		LikeResponse response = likeService.deleteLike(securityUser.getId(), request);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(response);
	}
}
