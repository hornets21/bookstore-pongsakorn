package com.example.Bookstore.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.Bookstore.dto.UserDTO;

@Repository
public interface CustomUserRepository {
	List<UserDTO> getUsers();
}
