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
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.Update;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.response.NoteResponse;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.service.NoteService;

@RestController
public class NotesController {
	@Autowired
	private NoteService noteService;

	
	/*
	 * API to add the Note Details
	 */
	@PostMapping(value = "/user/{token}/notes")
	public ResponseEntity<Response> createNotes(@RequestBody NoteDto notes, @PathVariable String token) {

		// notes.setUser(new User(Integer.parseInt(id)));
		Noteinfo note = noteService.addNotes(notes, token);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Note Details Saved Successfully", 200, notes));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("Already existing user", 400, notes));
		}
	}
	
	@PutMapping(value="/notes/{id}")
	public ResponseEntity<NoteResponse> updateNote(@PathVariable String id,@RequestBody UpdateNote dto){
		 List<Noteinfo> note = noteService.updateNotes(id,dto);
		 if(note!=null){
			 return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Note Title Updated", dto));
		 }
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new NoteResponse("Note Not Exist",  dto));
	}
	
	/*
	 * API to getting the Note Details By Id
	 */

	@GetMapping(value = "/notes/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable String id) {
		Noteinfo result = noteService.getNote(id);
		if (result != null) {
			// String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new NoteResponse("Note Not Exist",  result));
	}
	/*
	 * API to getting the Note Details By User_Id
	 */

	@GetMapping(value = "/notes/user/{id}")
	public ResponseEntity<NoteResponse> getNotesByUserId(@PathVariable String id) {
		List<Noteinfo> result = noteService.getNoteByUserId(id);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new NoteResponse("Not an existing user",  result));
	}
	/*
	 * API to deleting the Note Details By _Id
	 */
	@DeleteMapping(value = "/notes/{id}")
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable String id) {
		Noteinfo result = noteService.removeNotes(id);
		if (result == null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new NoteResponse("Not existing user",  result));
	}

	/*
	 * API to get The All Note Details
	 */
	
	@GetMapping("/user/notes")
	public List<Noteinfo> getAllNotes() {
		
		return noteService.getAllNotes();
	}

}
