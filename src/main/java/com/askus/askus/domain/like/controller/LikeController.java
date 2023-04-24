package com.askus.askus.domain.like.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.like.dto.LikeRequest;
import com.askus.askus.domain.like.dto.LikeResponse;
import com.askus.askus.domain.like.service.LikeService;
import com.askus.askus.domain.users.security.SecurityUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/likes")
@RequiredArgsConstructor
public class LikeController {
	private final LikeService likeService;

	@Operation(
		summary = "게시글 좋아요 추가",
		description = "게시글에 좋아요를 추가합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = LikeResponse.class)))
	@PostMapping
	public ResponseEntity<LikeResponse> addLike(
		@AuthenticationPrincipal SecurityUser securityUser,
		@RequestBody LikeRequest request
	) {
		LikeResponse response = likeService.addLike(securityUser.getId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	@Operation(
		summary = "게시글 좋아요 삭제",
		description = "게시글에 좋아요를 삭제합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "204", description = "No_Content")
	@DeleteMapping
	public ResponseEntity<LikeResponse> deleteLike(
		@AuthenticationPrincipal SecurityUser securityUser,
		@RequestBody LikeRequest request
	) {
		LikeResponse response = likeService.deleteLike(securityUser.getId(), request);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(response);
	}
}
