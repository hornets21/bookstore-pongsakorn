package com.example.Bookstore.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bookstore.jwt.JwtUtils;
import com.example.Bookstore.repository.UserRepository;
import com.example.Bookstore.request.LoginRequest;

@CrossOrigin
@RestController
public class RestLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestLoginController.class);
	
	AuthenticationManager authenticationManager;
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	JwtUtils jwtUtils;
	
	@Autowired
	public RestLoginController(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
		logger.info("start login with username : {}", loginRequest.getUsername());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ResponseEntity.ok().build();
	}
}
