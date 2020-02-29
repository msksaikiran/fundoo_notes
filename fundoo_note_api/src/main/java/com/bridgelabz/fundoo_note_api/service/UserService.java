package com.bridgelabz.fundoo_note_api.service;

import java.util.List;
import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.Update;
import com.bridgelabz.fundoo_note_api.entity.User;

public interface UserService {

	User login(String id);
	
    User register(Register userRecord);
	
	User forgotPassword(Update updateDto);
	
	List<User> getUsers();
	
	void removeUser(String id);

	Boolean verify(String token);

	

	

}