package com.askus.askus.domain.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.board.dto.BoardResponse;
import com.askus.askus.domain.board.service.BoardService;
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
public class BoardController {
	private final BoardService boardService;

	@Operation(
		summary = "게시글 등록",
		description = "현재 로그인된 사용자의 게시글을 작성합니다.",
		security = {@SecurityRequirement(name = "bearer-key")}
	)
	@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = BoardResponse.Post.class)))
	@PostMapping
	public ResponseEntity<BoardResponse.Post> addBoard(
		@AuthenticationPrincipal SecurityUser securityUser,
		BoardRequest.Post request
	) {
		BoardResponse.Post response = boardService.addBoard(securityUser.getId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	@GetMapping
	public List<BoardResponse.Summary> searchBoards(
		BoardRequest.Summary request
	) {
		return boardService.searchBoards(request);
	}

	@GetMapping("/{boardId}")
	public BoardResponse.Detail searchBoard(
		@PathVariable Long boardId
	) {
		return boardService.searchBoard(boardId);
	}

	@PatchMapping("/{boardId}")
	public BoardResponse.Patch updateBoard(
		@AuthenticationPrincipal SecurityUser securityUser,
		@PathVariable Long boardId,
		BoardRequest.Patch request
	) {
		return boardService.updateBoard(securityUser.getId(), boardId, request);
	}

	@DeleteMapping
	public ResponseEntity<BoardResponse.Delete> deleteBoard(
		BoardRequest.Delete request
	) {
		BoardResponse.Delete response = boardService.deleteBoard(request);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(response);
	}
}
