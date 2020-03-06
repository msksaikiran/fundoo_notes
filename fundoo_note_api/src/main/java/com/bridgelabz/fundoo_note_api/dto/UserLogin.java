package com.bridgelabz.fundoo_note_api.dto;

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

	private String userEmail;
	private String password;
	
	
}
