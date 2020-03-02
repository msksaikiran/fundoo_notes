package com.bridgelabz.fundoo_note_api.dto;

public class Update {

	private String newPassword;

	private String email;

	public String getPassword() {
		return newPassword;
	}

	public void setPassword(String password) {
		this.newPassword = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
