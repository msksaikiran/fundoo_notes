package com.bridgelabz.fundoo_note_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Register {
	private String name;
	private String email;
	private String password;
	private long number;
}
