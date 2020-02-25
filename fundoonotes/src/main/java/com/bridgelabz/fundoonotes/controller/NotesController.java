package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.entity.Noteinfo;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NotesController {
	@Autowired
	private NoteService noteService;

	@GetMapping("/notes")
	public List<Noteinfo> getAllUser() {
		return noteService.getAllNotes();
	}

	@PostMapping(value = "/notes/add-notes")
	public void addUser(@RequestBody Noteinfo notes) {

		noteService.addNotes(notes);
	}

	@GetMapping(value = "/notes/login/{id}")
	public Noteinfo loginUser(@RequestBody Noteinfo notes, @PathVariable String id) {
		Noteinfo result = noteService.getNotes(Integer.parseInt(id));
		if (result == null)
			throw new UserNotFoundException(id + " Record not Exist in Database");

		return result;

	}

	@DeleteMapping(value = "/notes/delete/{id}")
	public void deleteUser(@RequestBody Noteinfo notes, @PathVariable String id) {
		noteService.removeNotes(notes, id);
	}

}
