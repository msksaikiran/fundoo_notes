package com.bridgelabz.fundoonotesBackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Register {
	
	@NotNull(message = "Field Should not be Empty")
	@Email(message="Email Field should be proper")
	private String email;
	
	@Pattern(regexp = "^[A-z]{1}[a-z]{3}",message = "Name should be small letters")
	private String name;
	
	
	@Pattern(regexp = "^[A-z]{1}[a-z]{6}",message = "password Must contain 7 character 1st Capital and remain small")
	private String password;
	
	//@NumberFormat(pattern = "^[987][0-9]{9}")
   //@Pattern(regexp = "^[987][0-9]{9}",message = "Phone must be 10 numbers start with 9 r 8 r 7")
	private long number;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}

}
