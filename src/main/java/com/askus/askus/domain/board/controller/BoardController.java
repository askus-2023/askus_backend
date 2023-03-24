package com.askus.askus.domain.board.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.board.dto.BoardAddResponse;
import com.askus.askus.domain.board.service.BoardService;
import com.askus.askus.domain.users.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping()
	public ResponseEntity<BoardAddResponse> addBoard(
		@AuthenticationPrincipal SecurityUser securityUser,
		BoardAddRequest request
	) throws IOException {
		Long userId = securityUser.getId();
		BoardAddResponse boardAddResponse = boardService.addBoard(userId, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(boardAddResponse);
	}
}
