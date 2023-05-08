package com.askus.askus.global.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class SecurityUtil {

	public static String getAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}

