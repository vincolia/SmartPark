package com.smartpark.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenprovider;
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	private static final String STATIC_USERNAME = "admin";
	private static final String STATIC_PASSWORD = "password";
	
	public SecurityConfig(JwtTokenProvider jwtTokenprovider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtTokenprovider = jwtTokenprovider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
	
	@Bean
	UserDetailsService userDetailsService() {
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				if(STATIC_USERNAME.equals(username)) {
					return User.builder()
							.username(STATIC_USERNAME)
							.password(passwordEncoder().encode(STATIC_PASSWORD))
							.roles("USER")
							.build();
				}
				throw new UsernameNotFoundException("User not found");
				
			}
		};
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenprovider);
    	
    	return httpSecurity.csrf(csrf -> csrf.disable())
    			.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
    			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
    											.requestMatchers("/error").permitAll()
    											.anyRequest()
    											.authenticated()
    			)
    			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    			.build();
    }

}
