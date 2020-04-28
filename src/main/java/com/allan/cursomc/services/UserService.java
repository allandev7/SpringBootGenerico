package com.allan.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.allan.cursomc.security.UserSS;

public class UserService {
	public static UserSS getCurrentUser() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
