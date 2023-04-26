package com.askus.askus.global.error.exception;

/**
 * File RuntimeException for this Application
 */
public class FileException extends BadRequestException {

	static final String MESSAGE_KEY = "file";

	public FileException(String targetName, Object... keys) {
		super(MESSAGE_KEY, targetName, keys);
	}
}
