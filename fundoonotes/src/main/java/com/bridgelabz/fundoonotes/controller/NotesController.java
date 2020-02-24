package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController  
public class NotesController 
{  
    @Autowired  
    private NoteService noteService;   
    POST
    @RequestMapping("/notes")  
    public List<UserRecord> getAllUser()
    {  
        return noteService.getAllNotes();  
    }     
    @RequestMapping(value="/notes/add-notes", method=RequestMethod.POST)  
    public void addUser(@RequestBody UserRecord userRecord)
    { 
    	
        noteService.addNotes(userRecord);  
    }  
    @RequestMapping(value="/login/{id}",method=RequestMethod.GET)
    public UserRecord loginUser(@RequestBody UserRecord userRecord,@PathVariable String id)
    {  
        UserRecord result = noteService.getNotes(Integer.parseInt(id)); 
    	if(result==null)
    		throw new UserNotFoundException(id+" Record not Exist in Database");
    		
        return result;
       
    } 
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public void deleteUser(@RequestBody UserRecord userRecord,@PathVariable String id) {
    	 noteService.removeNotes(userRecord,id); 
    }
   
    
}  
