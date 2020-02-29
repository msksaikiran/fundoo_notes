package com.bridgelabz.fundoo_note_api.controller;


import java.util.List;
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
import com.bridgelabz.fundoo_note_api.dto.Login;
import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.Update;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.response.UserDetail;
import com.bridgelabz.fundoo_note_api.service.UserService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@RestController
public class UserController {

	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtGenerator generator;
	
	/*  API for user login  */
	
	@GetMapping(value = "/user/login/{token}")
	public ResponseEntity<UserDetail> loginUser(@PathVariable String token) {
		User result = userService.login(token);
		if (result != null) {
			String parseToken = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserDetail(parseToken, "You have Loggined in Successfully",result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("Login failed", "400-Not-Found", result));


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
	
	/*    API for verifying the token generated for the email     */
	
	@GetMapping(value="/verify/{token}")
	public ResponseEntity<Response> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verify(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified", 200, token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified", 400, token));
	}
	
	/*  API for user Forgot Passsword  */
	
	@PutMapping(value = "/user/forgot")
	public ResponseEntity<Response> forgetPassword(@RequestBody Update updateDto) {
		
		User result = userService.forgotPassword(updateDto);
		if(result!=null){
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password Updated Successfully", 200, User.class));
			}else{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("emailId doesn't matched", 402, updateDto.getEmail()));
			}
	}

	/*  API for Getting the all users */
	
/*	@GetMapping(value="/user")
	public List<User> getAllUsers() {
		return userService.getUsers();
	}
	
	/*  API for Deleting the user 
	
	@DeleteMapping(value = "/user/delete/{id}")
	public void deleteUser(@PathVariable String id) {
		User result = userService.removeUser(id);
		if(result!=null){
			System.out.println("@@@");
		}
	}
*/
}
