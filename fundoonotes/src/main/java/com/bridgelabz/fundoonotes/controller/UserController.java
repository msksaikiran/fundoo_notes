package com.bridgelabz.fundoonotes.controller;  
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;

//import com.bridgelabz.fundoonotes.configuration.ConfigurFile;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.service.UserService;

import java.util.List;

@RestController  
public class UserController 
{  
    @Autowired  
    private UserService userService;   
    
    @RequestMapping("/user")  
    public List<UserRecord> getAllUser(@RequestBody UserRecord userRecord)
    {  
        return userService.getUsers(userRecord);  
    }     
    @RequestMapping(value="/user/add-user", method=RequestMethod.POST)  
    public void addUser(@RequestBody UserRecord userRecord)
    { 
    	
        userService.addUser(userRecord);  
    }  
    @RequestMapping(value="/user/login/{id}",method=RequestMethod.GET)
    public UserRecord loginUser(@RequestBody UserRecord userRecord,@PathVariable String id)
    {  
        UserRecord result = userService.getUser(Integer.parseInt(id),userRecord); 
    	if(result==null)
    		throw new UserNotFoundException(id+" Record not Exist in Database");
    		
        return result;
       
    } 
    @RequestMapping(value="/user/delete/{id}",method=RequestMethod.DELETE)
    public void deleteUser(@RequestBody UserRecord userRecord,@PathVariable String id) {
    	 userService.removeUser(userRecord,id); 
    }
    @GetMapping("/hello")
    public String get() {
    	return "hello";
	}
    
}  
