package com.smartpark.security;

import java.security.Key;
import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class JwtTokenProvider {
	
	private String secretKey;
	private Long expiration;
	
	private Key key;
	
	@PostConstruct
	protected void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String createToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);
		
		Date now = new Date();
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + (expiration * 60 * 1000)))
				.signWith(key)
				.compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getUsername(String token) {
		
		return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
	}

}
