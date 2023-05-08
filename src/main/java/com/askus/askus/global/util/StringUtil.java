package com.askus.askus.global.util;

/**
 * Validating String
 * @Function - check if null or empty
 * */
public class StringUtil {
	public static boolean isNullOrEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
