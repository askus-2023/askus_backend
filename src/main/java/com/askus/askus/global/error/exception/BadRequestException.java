package com.askus.askus.global.error.exception;

/**
 * BadRequest RuntimeException for this Application
 */
public class BadRequestException extends KookleRuntimeException {

	protected static final String MESSAGE_KEY = "error.bad.request";

	public BadRequestException(String detailKey, Object... params) {
		super(MESSAGE_KEY + "." + detailKey, params);
	}
}
