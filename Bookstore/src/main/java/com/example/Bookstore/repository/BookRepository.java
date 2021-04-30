package com.example.Bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bookstore.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
}
