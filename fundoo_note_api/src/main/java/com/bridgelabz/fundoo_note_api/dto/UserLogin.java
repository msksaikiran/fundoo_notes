package com.bridgelabz.fundoo_note_api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLogin {

	@NotNull(message = "Field Should not be Empty")
	@Pattern(message = "dsdfadf", regexp = "[]")
	private String Email;
	private String password;
	
	
}
