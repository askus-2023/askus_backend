package com.askus.askus.global.error;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Response Format for the exception
 */
@Data
public class ErrorResponse {
	@NotNull
	@Schema(description = "예외의 HTTP 응답 코드")
	private final int code;
	@NotNull
	@Schema(description = "예외의 타입")
	private final String type;
	@NotNull
	@Schema(description = "예외 메시지")
	private final String message;

	public ErrorResponse(HttpStatus httpStatus, String type, String message) {
		this.code = httpStatus.value();
		this.type = type;
		this.message = message;
	}
}
