package com.askus.askus.global.error.exception;

/**
 * NotFound RuntimeException for this Application
 */
public class NotFoundException extends BadRequestException {

	static final String MESSAGE_KEY = "not-found";

	public NotFoundException(String targetName, Object... keys) {
		super(MESSAGE_KEY, targetName, keys);
	}
}
