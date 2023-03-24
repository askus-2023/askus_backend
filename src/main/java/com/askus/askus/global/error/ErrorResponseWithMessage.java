package com.askus.askus.global.error;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseWithMessage {
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	SIGNUP_BAD_REQUEST(HttpStatus.BAD_REQUEST, "이메일 혹은 비밀번호의 형식이 올바르지 않습니다."),
	INVALID_FIELD(HttpStatus.BAD_REQUEST, "{0}의 형식이 올바르지 않습니다.");

	private final HttpStatus status;
	private final String title;
	private final String message;

	ErrorResponseWithMessage(HttpStatus status, String message) {
		this(status, message, KookleRuntimeException.class);
	}

	ErrorResponseWithMessage(HttpStatus status, String message, Class<?> clazz) {
		this.status = status;
		this.title = clazz.getSimpleName();
		this.message = message;
	}

	public ErrorResponse toInstance(Object[] args) {
		String formattedMessage = MessageFormat.format(message, args);
		return new ErrorResponse(status.value(), title, formattedMessage);
	}
}
