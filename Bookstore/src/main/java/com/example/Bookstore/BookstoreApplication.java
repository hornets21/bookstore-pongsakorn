package com.example.Bookstore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.Bookstore.dto.BookDTO;
import com.example.Bookstore.entity.Book;
import com.example.Bookstore.repository.BookRepository;

@SpringBootApplication
public class BookstoreApplication {
	
	@Autowired
	BookRepository bookRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			ResponseEntity<BookDTO[]> books = restTemplate.getForEntity("https://scb-test-book-publisher.herokuapp.com/books", BookDTO[].class);
			ResponseEntity<BookDTO[]> booksRecommendation = restTemplate.getForEntity("https://scb-test-book-publisher.herokuapp.com/books/recommendation", BookDTO[].class);
			List<Book> saveBooks = new ArrayList<>();
			for (BookDTO obj : books.getBody()) {
				Book book = new Book();
				book.setBookId(obj.getId());
				book.setName(obj.getBook_name());
				book.setPrice(new BigDecimal(obj.getPrice()));
				book.setAuthor(obj.getAuthor_name());
				saveBooks.add(book);
			}
			
			for (BookDTO obj : booksRecommendation.getBody()) {
				long target = obj.getId();
				saveBooks.forEach(c -> { 
					if (c.getBookId() == target) {
						c.setRecommmended(true);
					}
				});
			}
			
			bookRepository.saveAll(saveBooks);
		};
	}
	
}
