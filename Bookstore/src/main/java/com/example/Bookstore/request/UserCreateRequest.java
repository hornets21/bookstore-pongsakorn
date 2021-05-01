package com.example.Bookstore.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequest {
	private String username;
	private String password;
	private String name;
	private String surname;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dateOfBirth;
}
