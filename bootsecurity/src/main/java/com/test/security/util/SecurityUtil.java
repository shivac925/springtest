package com.test.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	public static String getSessionToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			return (String) auth.getPrincipal();
		} else {
			return null;
		}
	}
	
}
