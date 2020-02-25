package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.UserRecord;


public interface UserService {

	
	public List<UserRecord> getUsers(UserRecord userRecord);

	public void addUser(UserRecord user) ;

	public UserRecord getUser(int id,UserRecord userRecord);

	public void removeUser(UserRecord userRecord, String id) ;

}