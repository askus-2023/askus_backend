package com.askus.askus.domain.board.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.board.dto.BoardAddResponse;
import com.askus.askus.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping()
	public ResponseEntity<BoardAddResponse> addBoard(
		BoardAddRequest request
	) throws IOException {
		//TODO: @AuthenticationPrincipal 활용 -> SecurityUser 타입 Authentication 객체에서 userId 가져올 것
		long id = 1L;

		BoardAddResponse boardAddResponse = boardService.addBoard(id, request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(boardAddResponse);
	}
}
