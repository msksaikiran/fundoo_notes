package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.Login;
import com.bridgelabz.fundoonotes.dto.Register;
import com.bridgelabz.fundoonotes.dto.Update;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetail;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import java.util.List;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtGenerator generator;
	
	/*  API for user login  */
	
	@GetMapping(value = "/user/login/{id}")
	public ResponseEntity<UserDetail> loginUser(@RequestBody Login userRecord, @PathVariable String id) {
		User result = userService.login(Integer.parseInt(id), userRecord);
		if (result != null) {
			String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Login Successfull", result.getName())
					.body(new UserDetail(token, 200,userRecord));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("Login failed", 400, userRecord));


	}
	/*  API for user register  */
	
	@PostMapping(value = "/user/add-user")
	public ResponseEntity<Response> register(@RequestBody Register userRecord) {

		User user = userService.register(userRecord);
		if(user!=null){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Registration Successfully", 200, userRecord));
		}else{
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response("Already existing user", 400, userRecord));
		}
	}
	
	/*API for verifying the token generated for the email*/
	
	@PostMapping("/verify/{token}")
	public ResponseEntity<Response> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verify(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200, token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified", 400, token));
	}
	
	/*  API for user Forgot Passsword  */
	
	@PutMapping(value = "/user/forgot/{token}")
	public ResponseEntity<Response> forgetPassword(@PathVariable String token,@RequestBody Update user) {
       
		User result = userService.forgotPassword(token,user);
		if(result!=null){
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password Updated Successfully", 200, user));
			}else{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("password doesn't matched", 402, token));
			}
	}

	/*  API for Getting the all users */
	
	@GetMapping("/user")
	public List<User> getAllUser(@RequestBody User userRecord) {
		return userService.getUsers(userRecord);
	}
	
	/*  API for Deleting the user */
	
	@DeleteMapping(value = "/user/delete/{id}")
	public void deleteUser(@RequestBody User userRecord, @PathVariable String id) {
		userService.removeUser(userRecord, id);
	}
	
}
