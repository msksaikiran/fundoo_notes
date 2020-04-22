package com.bridgelabz.fundoonotesBackend.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLogin {

	@NotNull(message = "Field Should not be Empty")
	
	@Email(message="Email Field should be proper")
	private String email;
	
	@Pattern(regexp = "^[a-zA-z0-9]{7}",message = "password Must contain 7 character no special characters")
	private String password;
	
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
	
	
}
