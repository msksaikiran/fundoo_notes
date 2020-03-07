package com.bridgelabz.fundoo_note_api.service;

import java.util.List;
import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.Update;
import com.bridgelabz.fundoo_note_api.dto.UserLogin;
import com.bridgelabz.fundoo_note_api.entity.User;

public interface UserService {

	User login(UserLogin user);

	User register(Register userRecord);

	User forgotPassword(String newPassword, String token);

	//List<User> getUsers();

	//User removeUser(String id);

	Boolean verify(String token);

    String emailVerify(String email);

    User getUser(String token);	


}