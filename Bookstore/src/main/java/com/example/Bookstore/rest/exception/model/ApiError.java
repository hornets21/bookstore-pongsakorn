package com.example.Bookstore.rest.exception.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ApiError(HttpStatus status, String message, String error) {
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
}
