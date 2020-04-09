package com.bridgelabz.fundoonote.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoonote.dto.EmailVeify;
import com.bridgelabz.fundoonote.dto.Register;
import com.bridgelabz.fundoonote.dto.ResetPassword;
import com.bridgelabz.fundoonote.dto.UserLogin;
import com.bridgelabz.fundoonote.entity.User;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.response.UserResponse;
import com.bridgelabz.fundoonote.service.UserService;

@RestController
@RequestMapping("/users")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment env;

	
	/*
	 * API for user login
	 */

	@PostMapping(value = "/login")
	public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody UserLogin user, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new UserResponse(result.getAllErrors().get(0).getDefaultMessage(), "200"));
		
		String token = userService.login(user);
		if (token != null) {
			UserResponse userr = new UserResponse(token, env.getProperty("100"), user);
			return new ResponseEntity<>(userr,HttpStatus.OK);

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(env.getProperty("106"), "", user));

	}
	
	@PostMapping(value = "/getimageurl/{token}")
	public ResponseEntity<UserResponse> imageurl(@PathVariable String token) {
		
		User url = userService.getImageUrl(token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new UserResponse(env.getProperty("101"), "200-ok", url));

	}

	/*
	 * API for user register
	 */

	@PostMapping(value = "/add-user")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody Register userRecord, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new UserResponse(result.getAllErrors().get(0).getDefaultMessage(), "200"));
		User user = userService.register(userRecord);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse(env.getProperty("101"), "200-ok", userRecord));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new UserResponse(env.getProperty("106"), "", userRecord));
	}

	/*
	 * API for verify the user when he password want to update
	 */
	@PostMapping(value = "/emailId")
	public ResponseEntity<UserResponse> emailVerify(@RequestBody EmailVeify emailId) {

		String result = userService.emailVerify(emailId);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserResponse(result,env.getProperty("107"),200));
		}
		return null;
	}

	/*
	 * API for user Forgot Passsword
	 * 
	 */
	@PutMapping(value = "/forgotPassword/{token}")
	public ResponseEntity<UserResponse> forgetPassword(@PathVariable String token,@RequestBody ResetPassword rest) {

		User result = userService.forgotPassword(rest.getNewPassword(),token);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(env.getProperty("108"), "200-ok", result));
		}
		return null;
	}

	/*
	 * API for verifying the token generated for the email
	 */

	@GetMapping(value = "/verify/{token}")
	public ResponseEntity<Response> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verify(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(env.getProperty("109"), 200));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("no.." + env.getProperty("109"), 401));

	}

	/*
	 * API for getting the user details based on the token
	 */
	@GetMapping(value = "/{token}")
	public User getUser(@PathVariable String token,@RequestParam(name = "isCacheable") boolean isCacheable) {
		return userService.getUser(token,isCacheable);
	}

	//@PostMapping(value = "/getimageurl/{token}")
	@PostMapping(value="/uploadProfile/{token}")
    public Map<String, String> uploadProfile(@RequestBody MultipartFile file,@PathVariable String token)
    {
        this.userService.uploadFileToS3Bucket(file, true,token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");

        return response;
    }

    @DeleteMapping(value="/deleteProfile")
    public Map<String, String> deleteProfile(@RequestParam("file_name") String fileName,@RequestPart("token") String token)
    {
        this.userService.deleteFileFromS3Bucket(fileName,token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");

        return response;
    }
    
    
	/* API for Getting the all users */

	/*
	 * @GetMapping(value="/user") public List<User> getAllUsers() { return
	 * userService.getUsers(); }
	 
	 * /* API for Deleting the user
	 * 
	 * @DeleteMapping(value = "/user/delete/{id}") public void
	 * deleteUser(@PathVariable String id) { User result =
	 * userService.removeUser(id); if(result!=null){ System.out.println("@@@"); } }
	 */
}
