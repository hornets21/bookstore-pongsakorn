package com.example.Bookstore.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bookstore.entity.Book;
import com.example.Bookstore.serivce.BookService;

@RestController
public class RestBookController {

	@Autowired
	BookService bookService;
	
	@GetMapping("/books")
	public List<Book> getBooks() {
		return bookService.getBooks();
	}
}
