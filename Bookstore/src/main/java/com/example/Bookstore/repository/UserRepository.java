package com.example.Bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bookstore.entity.User;
import com.example.Bookstore.repository.custom.CustomUserRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
	Optional<User> findByUsername(String username);
}
