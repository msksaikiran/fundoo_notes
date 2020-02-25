package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.User;

public interface UserService {

	public User login(int id, User userRecord);
	
	public void register(User user);
	
	public void forgotPassword(String email, String password);
	
	public List<User> getUsers(User userRecord);
	
	public void removeUser(User userRecord, String id);

	

}