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

import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.service.UserService;

import java.util.List;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/user/login/{id}")
	public User loginUser(@RequestBody User userRecord, @PathVariable String id) {
		User result = userService.login(Integer.parseInt(id), userRecord);
		if (result == null)
			throw new UserNotFoundException(id + " Record not Exist in Database");

		return result;

	}
	
	@PostMapping(value = "/user/add-user")
	public void register(@RequestBody User userRecord) {

		userService.register(userRecord);
	}
	
	@PostMapping(value = "/user/forgot/{email}/{password}")
	public void forgetPassword(@PathVariable String email,@PathVariable String password) {

		userService.forgotPassword(email,password);
	}
	@GetMapping("/user")
	public List<User> getAllUser(@RequestBody User userRecord) {
		return userService.getUsers(userRecord);
	}

	@DeleteMapping(value = "/user/delete/{id}")
	public void deleteUser(@RequestBody User userRecord, @PathVariable String id) {
		userService.removeUser(userRecord, id);
	}

}
