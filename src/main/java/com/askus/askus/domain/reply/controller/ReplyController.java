package com.askus.askus.domain.reply.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.reply.dto.ReplyRequest;
import com.askus.askus.domain.reply.dto.ReplyResponse;
import com.askus.askus.domain.reply.service.ReplyService;
import com.askus.askus.domain.users.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService replyService;

	@PostMapping("/{boardId}/replies")
	public ResponseEntity<ReplyResponse.Post> addReply(
		@AuthenticationPrincipal SecurityUser securityUser,
		@PathVariable Long boardId,
		ReplyRequest.Post request
	) {
		ReplyResponse.Post response = replyService.addReply(securityUser.getId(), boardId, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	@PatchMapping("/{boardId}/replies/{replyId}")
	public ReplyResponse.Patch updateReply(
		@PathVariable Long boardId,
		@PathVariable Long replyId,
		ReplyRequest.Patch request
	) {
		return replyService.updateReply(boardId, replyId, request);
	}

	@DeleteMapping("/{boardId}/replies/{replyId}")
	public ResponseEntity<ReplyResponse.Delete> deleteReply(
		@PathVariable Long boardId,
		@PathVariable Long replyId
	) {
		ReplyResponse.Delete response = replyService.deleteReply(boardId, replyId);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(response);
	}
}
