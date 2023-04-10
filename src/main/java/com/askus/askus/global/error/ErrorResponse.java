package com.askus.askus.global.error;

import static org.apache.commons.lang3.ObjectUtils.*;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse {
	@NotNull
	private final int code;
	@NotNull
	private final String title;
	@NotNull
	private final String message;

	public ErrorResponse(HttpStatus httpStatus, Exception exception) {
		this(
			httpStatus.value(),
			exception.getClass().getSimpleName(),
			defaultIfNull(exception.getMessage(), "")
		);
	}

	public ErrorResponse(int code, String title, String message) {
		this.code = code;
		this.title = title;
		this.message = message;
	}
}
