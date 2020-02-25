package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

//import com.bridgelabz.fundoonotes.configuration.ConfigurFile;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.service.UserService;

import java.util.List;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public List<UserRecord> getAllUser(@RequestBody UserRecord userRecord) {
		return userService.getUsers(userRecord);
	}

	@PostMapping(value = "/user/add-user")
	public void addUser(@RequestBody UserRecord userRecord) {

		userService.addUser(userRecord);
	}

	@GetMapping(value = "/user/login/{id}")
	public UserRecord loginUser(@RequestBody UserRecord userRecord, @PathVariable String id) {
		UserRecord result = userService.getUser(Integer.parseInt(id), userRecord);
		if (result == null)
			throw new UserNotFoundException(id + " Record not Exist in Database");

		return result;

	}

	@DeleteMapping(value = "/user/delete/{id}")
	public void deleteUser(@RequestBody UserRecord userRecord, @PathVariable String id) {
		userService.removeUser(userRecord, id);
	}

	@GetMapping("/hello")
	public String get() {
		return "hello";
	}

}
