package com.askus.askus.global.util;

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
