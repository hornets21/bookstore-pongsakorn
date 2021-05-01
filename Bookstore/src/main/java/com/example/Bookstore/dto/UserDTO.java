package com.example.Bookstore.dto;

import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDTO {
	
	public static final String STATEMENT = "UserDTO";
	
	private String name;
	private String surname;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dateOfBirth;
	
	private int[] books;
		
	public UserDTO(String name, String surname, Date dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
	}

	@SqlResultSetMapping(name = STATEMENT, classes = { 
			@ConstructorResult(targetClass = UserDTO.class, columns = {
				@ColumnResult(name = "name", type = String.class),
				@ColumnResult(name = "surname", type = String.class),
				@ColumnResult(name = "dateOfBirth", type = Date.class),
			}) 
	})
	@Entity
	class MappingEntity {
		@Id
		int id;
	}
}