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
import com.example.Bookstore.entity.OrderBooks;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.repository.BookRepository;
import com.example.Bookstore.repository.OrderBooksRepository;
import com.example.Bookstore.serivce.UserDetailServiceImpl;

@SpringBootApplication
public class BookstoreApplication {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserDetailServiceImpl userDetailService;
	
	@Autowired
	OrderBooksRepository orderBooksRepository;
	
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
			
			User user = userDetailService.findUserById(1L).orElseThrow();
			Book bookId1 = bookRepository.findById(1L).orElseThrow();
			Book bookId4 = bookRepository.findById(4L).orElseThrow();
			
			OrderBooks orderBooks1 = new OrderBooks();
			orderBooks1.setBook(bookId1);
			orderBooks1.setUser(user);
			orderBooksRepository.save(orderBooks1);
			
			OrderBooks orderBooks4 = new OrderBooks();
			orderBooks4.setBook(bookId4);
			orderBooks4.setUser(user);
			orderBooksRepository.save(orderBooks4);

		};
	}
	
}
