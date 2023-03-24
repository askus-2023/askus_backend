package com.askus.askus.global.error;

import static org.apache.commons.lang3.ObjectUtils.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handlerRuntimeException(Exception e) {
		log.info(messageIfNull(e.getMessage()), e);
		return new ErrorResponse(HttpStatus.BAD_REQUEST, e);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ErrorResponse noHandlerFoundException(Exception e) {
		return new ErrorResponse(HttpStatus.NOT_FOUND, e);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handlerException(Exception e) {
		log.error(messageIfNull(e.getMessage()), e);
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

	private String messageIfNull(String message) {
		return defaultIfNull(message, "exception with no message");
	}
}
