package com.askus.askus.global.error.exception;

/**
 * Root RuntimeException for this Application
 */
public class KookleRuntimeException extends RuntimeException {
	public KookleRuntimeException() {
	}

	public KookleRuntimeException(String message) {
		super(message);
	}

	public KookleRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public KookleRuntimeException(Throwable cause) {
		super(cause);
	}

	public KookleRuntimeException(
		String message,
		Throwable cause,
		boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
