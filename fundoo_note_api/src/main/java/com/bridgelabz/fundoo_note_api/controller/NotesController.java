package com.bridgelabz.fundoo_note_api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.exception.UserNotFoundException;
import com.bridgelabz.fundoo_note_api.response.ListResponse;
import com.bridgelabz.fundoo_note_api.response.NoteResponse;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.response.UserDetail;
import com.bridgelabz.fundoo_note_api.service.NoteService;

@RestController
public class NotesController {
	@Autowired
	private NoteService noteService;

	@GetMapping("/user/notes")
	public List<Noteinfo> getAllNotes() {
		return noteService.getAllNotes();
	}

	@PostMapping(value = "/user/{id}/notes")
	public ResponseEntity<Response> addNotes(@RequestBody NoteDto notes,@PathVariable String id) {

		//notes.setUser(new User(Integer.parseInt(id)));
		Noteinfo note = noteService.addNotes(notes,id);
		if(note!=null){
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Registration Successfully", 200, notes));
			}else{
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("Already existing user", 400, notes));
			}
	}

	@GetMapping(value = "/notes/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable String id) {
		Noteinfo result = noteService.getNote(id);
		if (result != null) {
			//String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", result.getTitle())
					.body(new NoteResponse("200-OK",result));
		}
			throw new UserNotFoundException(id + " Record not Exist in Database");
	}
	
	@GetMapping(value = "/notes/user/{id}")
	public ResponseEntity<ListResponse> getUserNotesById(@PathVariable String id) {
		List<Noteinfo> result = noteService.getNoteByUserId(id);
		if (result != null) {
			//return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new ListResponse("200-OK",result));
		}
			throw new UserNotFoundException(id + " Record not Exist in Database");
	}


	@DeleteMapping(value = "/notes/{id}")
	public void deleteNote(@PathVariable String id) {
		noteService.removeNotes(id);
	}

}
