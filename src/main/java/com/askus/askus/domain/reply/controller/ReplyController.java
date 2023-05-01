package com.askus.askus.domain.reply.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.like.dto.LikeResponse;
import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;
import com.askus.askus.domain.reply.service.ReplyService;
import com.askus.askus.domain.users.security.SecurityUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService replyService;

	@Operation(
		summary = "게시글 댓글 등록",
		description = "게시글에 댓글을 작성합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = LikeResponse.class)))
	@PostMapping("/{boardId}/replies")
	public ResponseEntity<ReplyResponse> addReply(
		@AuthenticationPrincipal SecurityUser securityUser,
		@PathVariable Long boardId,
		@RequestBody ReplyRequest request
	) {
		ReplyResponse response = replyService.addReply(securityUser.getId(), boardId, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	@GetMapping("/{boardId}/replies")
	public List<ReplyResponse> getReplies(
		@AuthenticationPrincipal SecurityUser securityUser,
		@PathVariable Long boardId
	) {
		return replyService.searchReplies(securityUser.getId(), boardId);
	}

	@Operation(
		summary = "게시글 댓글 수정",
		description = "게시글에 댓글을 수정합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LikeResponse.class)))
	@PatchMapping("/{boardId}/replies/{replyId}")
	public ReplyResponse updateReply(
		@PathVariable Long boardId,
		@PathVariable Long replyId,
		@RequestBody ReplyRequest request
	) {
		return replyService.updateReply(boardId, replyId, request);
	}

	@Operation(
		summary = "게시글 댓글 삭제",
		description = "게시글에 댓글을 삭제합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "204", description = "No_Content")
	@DeleteMapping("/{boardId}/replies/{replyId}")
	public ResponseEntity<Void> deleteReply(
		@PathVariable Long boardId,
		@PathVariable Long replyId
	) {
		replyService.deleteReply(boardId, replyId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
