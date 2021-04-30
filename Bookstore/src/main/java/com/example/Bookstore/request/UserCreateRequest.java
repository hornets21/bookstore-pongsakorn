package com.example.Bookstore.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequest {
	
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String name;
	private String surname;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dateOfBirth;
}
