package com.nestorvave.blog.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nestorvave.blog.domain.dtos.Login.AuthResponse;
import com.nestorvave.blog.domain.dtos.Login.LoginRequest;
import com.nestorvave.blog.services.Auth.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(),
				loginRequest.getPassword());
		String tokenValue = authenticationService.generateToken(userDetails);
		AuthResponse authResponse = AuthResponse.builder().token(tokenValue).expiresIn(86400L).build();
		return ResponseEntity.ok(authResponse);
	}

}
