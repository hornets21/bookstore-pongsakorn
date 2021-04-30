package com.example.Bookstore.serivce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Bookstore.dto.UserDTO;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.entity.UserDetailsImpl;
import com.example.Bookstore.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found with username : " + username));
		return UserDetailsImpl.build(user);
	}
	
	public User saveOrUpdate(User user) {
		return userRepository.save(user);
	}
	
	public List<UserDTO> getUsers() {
		return userRepository.getUsers();
	}
}
