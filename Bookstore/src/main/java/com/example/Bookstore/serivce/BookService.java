package com.example.Bookstore.serivce;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository bookRepository;
	
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}
	
	public Optional<Book> findBookById(Long id) {
		return bookRepository.findById(id);
	}
}
