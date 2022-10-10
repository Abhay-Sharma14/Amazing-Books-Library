package com.spring.boot.auth.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.boot.auth.service.request.LoginRequest;
import com.spring.boot.auth.service.util.JwtUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/auth/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		String token = jwtUtil.generateToken(loginRequest);

		return new ResponseEntity<String>(token, HttpStatus.OK);
	}


}
