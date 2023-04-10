package com.askus.askus.domain.reply.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.reply.dto.ReplyAddRequest;
import com.askus.askus.domain.reply.dto.ReplyAddResponse;
import com.askus.askus.domain.reply.service.ReplyService;
import com.askus.askus.domain.users.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService replyService;

	@PostMapping("/{boardId}/replies")
	public ResponseEntity<ReplyAddResponse> addReply(
		@AuthenticationPrincipal SecurityUser users,
		@PathVariable long boardId,
		ReplyAddRequest request
	) {
		ReplyAddResponse response = replyService.addReply(users.getId(), boardId, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}
}
