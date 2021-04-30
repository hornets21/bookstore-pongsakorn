package com.example.Bookstore.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.example.Bookstore.dto.UserDTO;
import com.example.Bookstore.repository.custom.CustomUserRepository;

@Repository
public class UserRepositoryImpl implements CustomUserRepository {
	
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDTO> getUsers() {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT u.name, u.surname, u.date_of_birth as dateOfBirth FROM user u ";
		Query query = session.createNativeQuery(sql, UserDTO.STATEMENT);
		List<UserDTO> results = query.getResultList();
		return results;
	}
}
