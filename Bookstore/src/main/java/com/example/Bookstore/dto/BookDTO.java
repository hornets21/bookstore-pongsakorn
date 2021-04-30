package com.example.Bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	private String book_name;
	private long id;
	private String price;	
	private String author_name;
}
