package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.Noteinfo;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NotesController {
	@Autowired
	private NoteService noteService;

	@GetMapping("/user/{id}/notes")
	public List<Noteinfo> getAllNotes(@PathVariable String token) {
		return noteService.getAllNotes(token);
	}

	@PostMapping(value = "/user/{id}/notes")
	public void addNotes(@RequestBody NoteDto notes,@PathVariable String token) {

		//user.getId();
		//notes.setUser(new User(Integer.parseInt(id)));
		Noteinfo note = noteService.addNotes(notes,token);
		if(note!=null){
			System.out.println("SSSS.....");
		}
	}

	@GetMapping(value = "/user/{id}/notes/{id}")
	public Noteinfo getNote(@RequestBody Noteinfo notes,@PathVariable String noteid) {
		Noteinfo result = noteService.getNote(noteid);
		if (result == null)
			throw new UserNotFoundException(noteid + " Record not Exist in Database");

		return result;

	}

	@DeleteMapping(value = "/user/{id}/notes/{id}")
	public void deleteNote(@RequestBody Noteinfo notes, @PathVariable String id) {
		noteService.removeNotes(notes, id);
	}

}
