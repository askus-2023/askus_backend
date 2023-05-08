package com.askus.askus.global.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for the exception
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSourceAccessor messageSourceAccessor;

	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(KookleRuntimeException.class)
	public ErrorResponse handlerKookleRuntimeException(KookleRuntimeException exception) {
		log.error(exception.getMessage(), exception, exception.getParams());
		String type = exception.getClass().getSimpleName();
		String message = messageSourceAccessor.getMessage(exception.getMessageKey(), exception.getParams());
		return new ErrorResponse(BAD_REQUEST, type, message);
	}

	@ResponseStatus(BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handlerRuntimeException(Exception exception) {
		log.error(exception.getMessage(), exception);
		String type = exception.getClass().getSimpleName();
		String message = exception.getMessage();
		return new ErrorResponse(BAD_REQUEST, type, message);
	}

	@ResponseStatus(NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ErrorResponse handlerNoHandlerFoundException(Exception exception) {
		log.error(exception.getMessage(), exception);
		String type = exception.getClass().getSimpleName();
		String message = exception.getMessage();
		return new ErrorResponse(NOT_FOUND, type, message);
	}

	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handlerException(Exception exception) {
		log.error(exception.getMessage(), exception);
		String type = exception.getClass().getSimpleName();
		String message = exception.getMessage();
		return new ErrorResponse(INTERNAL_SERVER_ERROR, type, message);
	}
}
