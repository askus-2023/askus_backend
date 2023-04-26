package com.askus.askus.global.error.exception;

/**
 * MissMatch RuntimeException for this Application
 */
public class MissMatchException extends BadRequestException {

	static final String MESSAGE_KEY = "miss-match";

	public MissMatchException(String targetName, Object key) {
		super(MESSAGE_KEY, targetName, key);
	}
}
