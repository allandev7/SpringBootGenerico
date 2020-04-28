package com.allan.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.allan.cursomc.security.JWTUtil;
import com.allan.cursomc.security.UserSS;
import com.allan.cursomc.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResource {
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		UserSS user = UserService.getCurrentUser();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer "+ token);
		return ResponseEntity.noContent().build();
	}
}
