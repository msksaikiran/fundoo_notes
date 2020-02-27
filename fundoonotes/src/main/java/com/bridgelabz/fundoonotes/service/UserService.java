package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.Login;
import com.bridgelabz.fundoonotes.dto.Register;
import com.bridgelabz.fundoonotes.dto.Update;
import com.bridgelabz.fundoonotes.entity.User;

public interface UserService {

	public User login(int id, Login userRecord);
	
	public User register(Register userRecord);
	
	public User forgotPassword(String token,Update user);
	
	public List<User> getUsers(User userRecord);
	
	public void removeUser(User userRecord, String id);

	Boolean verify(String token);

	

}