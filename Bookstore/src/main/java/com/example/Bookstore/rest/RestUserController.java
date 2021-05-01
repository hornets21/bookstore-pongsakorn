package com.example.Bookstore.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.Bookstore.dto.UserDTO;
import com.example.Bookstore.entity.Book;
import com.example.Bookstore.entity.OrderBooks;
import com.example.Bookstore.entity.User;
import com.example.Bookstore.entity.UserDetailsImpl;
import com.example.Bookstore.repository.OrderBooksRepository;
import com.example.Bookstore.request.UserCreateRequest;
import com.example.Bookstore.serivce.BookService;
import com.example.Bookstore.serivce.UserDetailServiceImpl;

@RestController
public class RestUserController {
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	OrderBooksRepository orderBooksRepository;
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(HttpServletRequest request) {
		UserDetailsImpl userPrincipal = getUserDetailsFromAuthentication();
		if (null == userPrincipal) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found.");
		}
		UserDTO user = new UserDTO(userPrincipal.getUser().getName(), userPrincipal.getUser().getSurname(), userPrincipal.getUser().getDateOfbirth());
		user.setBooks(orderBooksRepository.findOrderByUserId(userPrincipal.getUser().getUserId()));
	    return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
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
	
	@PostMapping("/users/orders")
	public ResponseEntity<?> informationOrder(@RequestBody Map<String, Object> payload) {
		
		UserDetailsImpl userPrincipal = getUserDetailsFromAuthentication();
		if (null == userPrincipal) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found.");
		}
		
		Object orders = payload.get("orders");
		if (null != orders) {
			long[] id_orders = Arrays.stream(orders.toString().substring(1, orders.toString().length()-1).split(","))
				    .map(String::trim).mapToLong(Long::parseLong).toArray();
			BigDecimal sum = new BigDecimal(0);
			List<OrderBooks> saveOrderBooks = new ArrayList<>();
			for (long id : id_orders) {

				Book book = bookService.findBookById(id).orElseThrow();
				
				OrderBooks orderBook = new OrderBooks();
				orderBook.setUser(userPrincipal.getUser());
				orderBook.setBook(book);
				
				saveOrderBooks.add(orderBook);
				
				sum = sum.add(book.getPrice());
			}
			orderBooksRepository.saveAll(saveOrderBooks);
			Map<String, String> result = new HashMap<String, String>();
			result.put("price", String.valueOf(sum));
			return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
		}
		return null;
	}
	
	@Transactional
	@DeleteMapping("/users")
	public ResponseEntity<?> deleteUser() {
		UserDetailsImpl userPrincipal = getUserDetailsFromAuthentication();
		if (null == userPrincipal) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found.");
		}
		User user = userPrincipal.getUser();
		orderBooksRepository.deleteOrderBookByUserId(user.getUserId());
		userDetailService.deleteUser(user);
		return ResponseEntity.ok().build();
	}
	
	private UserDetailsImpl getUserDetailsFromAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return (UserDetailsImpl) authentication.getPrincipal(); 
		}
		return null;
	}
}
