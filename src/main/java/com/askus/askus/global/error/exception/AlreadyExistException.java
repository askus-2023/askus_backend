package com.askus.askus.global.error.exception;

/**
 * AlreadyExist RuntimeException for this Application
 */
public class AlreadyExistException extends BadRequestException {

	static final String MESSAGE_KEY = "already-exist";

	public AlreadyExistException(String targetName, Object... keys) {
		super(MESSAGE_KEY, targetName, keys);
	}
}
