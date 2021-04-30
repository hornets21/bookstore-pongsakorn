package com.example.Bookstore.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bookstore.dto.UserDTO;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.entity.UserDetailsImpl;
import com.example.Bookstore.request.UserCreateRequest;
import com.example.Bookstore.serivce.UserDetailServiceImpl;

@RestController
public class RestUserController {
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			UserDTO user = new UserDTO(userPrincipal.getUser().getName(), userPrincipal.getUser().getSurname(), userPrincipal.getUser().getDateOfbirth());
		    return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		}
		return null;
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
		try {
			User user = new User();
			user.setUsername(userCreateRequest.getUsername());
			user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
			user.setName(userCreateRequest.getName());
			user.setSurname(userCreateRequest.getSurname());
			user.setDateOfbirth(userCreateRequest.getDateOfBirth());
			user = userDetailService.saveOrUpdate(user);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw e;
		}
	}
}
