package com.smartpark.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.smartpark.dto.AuthRequest;
import com.smartpark.dto.AuthResponse;
import com.smartpark.security.JwtTokenProvider;

@Service
public class AuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public AuthResponse login(AuthRequest authRequest) {
		logger.info("Starting AuthService.login() - {}", authRequest.getUsername());
		
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
				);
		
		return new AuthResponse(jwtTokenProvider.createToken(authentication.getName()));
	}
	
}
