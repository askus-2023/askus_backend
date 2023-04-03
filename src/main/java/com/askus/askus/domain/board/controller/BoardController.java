package com.askus.askus.domain.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(HttpStatus.CREATED)
	public BoardAddResponse addBoard(
		@AuthenticationPrincipal SecurityUser securityUser,
		BoardAddRequest request
	) {
		Long userId = securityUser.getId();
		return boardService.addBoard(userId, request);
	}
}
