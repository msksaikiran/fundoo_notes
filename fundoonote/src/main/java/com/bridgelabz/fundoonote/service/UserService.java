package com.bridgelabz.fundoonote.service;

import java.util.List;

import com.bridgelabz.fundoonote.dto.Register;
import com.bridgelabz.fundoonote.dto.UpdatePassword;
import com.bridgelabz.fundoonote.dto.UserLogin;
import com.bridgelabz.fundoonote.entity.User;

public interface UserService {

	String login(UserLogin user);

	User register(Register userRecord);

	User forgotPassword(String newPassword, String token);

	//List<User> getUsers();

	//User removeUser(String id);

	Boolean verify(String token);

    String emailVerify(String email);

    User getUser(String token, boolean isCacheable);
    
    	


}