package com.smartpark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartpark.dto.AuthRequest;
import com.smartpark.dto.AuthResponse;
import com.smartpark.service.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
		
		return ResponseEntity.ok(authService.login(authRequest));
	}

}
