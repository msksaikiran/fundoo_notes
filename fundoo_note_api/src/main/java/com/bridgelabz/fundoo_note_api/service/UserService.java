package com.bridgelabz.fundoo_note_api.service;

import java.util.List;

import com.bridgelabz.fundoo_note_api.dto.Login;
import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.Update;
import com.bridgelabz.fundoo_note_api.entity.User;

public interface UserService {

	public User login(int id, Login userRecord);
	
	public User register(Register userRecord);
	
	public User forgotPassword(String token,Update user);
	
	public List<User> getUsers();
	
	public void removeUser(User userRecord, String id);

	Boolean verify(String token);

	

}