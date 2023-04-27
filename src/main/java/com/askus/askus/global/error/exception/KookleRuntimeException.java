package com.askus.askus.global.error.exception;

/**
 * Root RuntimeException for this Application
 */
public abstract class KookleRuntimeException extends RuntimeException {

	private final String messageKey;
	private final Object[] params;

	public KookleRuntimeException(String messageKey, Object[] params) {
		this.messageKey = messageKey;
		this.params = params;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Object[] getParams() {
		return params;
	}
}
