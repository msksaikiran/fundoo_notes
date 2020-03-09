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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.response.NoteResponse;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.service.NoteService;
import com.bridgelabz.fundoo_note_api.dto.ReminderDto;

@RestController
@RequestMapping("/notes")
public class NotesController {
	@Autowired
	private NoteService noteService;

	/*
	 * API to add the Note Details
	 */
	@PostMapping(value = "/users/{token}")
	public ResponseEntity<NoteResponse> createNotes(@RequestBody NoteDto notes, @PathVariable String token) {

		// notes.setUser(new User(Integer.parseInt(id)));
		Noteinfo note = noteService.addNotes(notes, token);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse("Note Details Saved Successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new NoteResponse("Already existing user"));
		}
	}

	/*
	 * API to Update the Note Details By Id
	 */
	@PutMapping(value = "/{id}/users/{token}")
	public ResponseEntity<NoteResponse> updateNote(@PathVariable String token, @PathVariable String id,
			@RequestBody UpdateNote dto) {
		List<Noteinfo> note = noteService.updateNotes(token, id, dto);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Note Title Updated"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist"));
	}

	/*
	 * API to getting the Note Details By Id
	 */

	@GetMapping(value = "/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable String id) {
		Noteinfo result = noteService.getNote(id);
		if (result != null) {
			// String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("200-OK"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist"));
	}
	/*
	 * API to getting the Note Details By User_Id
	 */

	@GetMapping(value = "/users/{token}")
	public ResponseEntity<NoteResponse> getNotesByUserId(@PathVariable String token) {
		List<Noteinfo> result = noteService.getNoteByUserId(token);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header(token, "sucess")
					.body(new NoteResponse("200-OK"));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new NoteResponse("Not an existing user"));
	}

	/*
	 * API to deleting the Note Details By _Id
	 */
	@DeleteMapping(value = "/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable String noteId, @PathVariable String token) {
		Noteinfo result = noteService.removeNotes(token, noteId);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new NoteResponse("Notes Deleted"));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new NoteResponse("Not existing user"));
	}

	/*
	 * API for pin a Note
	 */
	@PutMapping("/pin/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> pin(@PathVariable String noteId, @PathVariable String token) {
		   noteService.pinNote(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse("note pinned"));
	}

	/* API for archieve a Note */
	@PutMapping("/archieve/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> archieve(@PathVariable String noteId, @PathVariable String token) {
		    noteService.archieveNote(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse("note archieved"));
	}

	/*
	 * API for updating color to a Note
	 */
	@PutMapping("/{colour}/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> addColour(@PathVariable String noteId, @PathVariable String colour,
			@PathVariable String token) {
		   noteService.addColour(noteId, token, colour);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse("note colour changed"));

	}

	/* API for getting all archieve notes Notes */
	@GetMapping("/notes/getAllArchieve/users/{token}")
	public ResponseEntity<NoteResponse> getArchieve(@PathVariable String token) {
		  List<Noteinfo> note = noteService.getarchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse(" archieved notes"));
	}

	/*
	 * API for getting all trashed Notes
	 */
	@GetMapping("/getAlltrashed/users/{token}")
	public ResponseEntity<NoteResponse> getTrashed(@PathVariable String token) {
	        List<Noteinfo> note = noteService.getAlltrashednotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse(" trashed notes"));
	}

	/*
	 * API for getting all Pinned Notes
	 */
	@GetMapping("/getAllPinned/users/{token}")
	public ResponseEntity<NoteResponse> getPinned(@PathVariable String token) {
	        List<Noteinfo> note = noteService.getAllPinneded(token);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse(" pinned notes"));
	}

	/*
	 * API for adding remainder to Notes
	 */
	@PostMapping("/addremainder/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> addRemainder(@PathVariable String token, @PathVariable String noteId,
			@RequestBody ReminderDto remainder) {
		     String notes = noteService.addReminder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse(" reminder Added to the notes"));
	}

	/*
	 * API for removing remainder Notes
	 */
	@DeleteMapping("/removeRemainder/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> removeRemainder(@PathVariable String token, @PathVariable String noteId) {
		      noteService.removeReminder(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse("Reminder notes Removed"));
	}
	/*
	 * API to get The All Note Details
	 */

	@GetMapping("/user/notes")
	public List<Noteinfo> getAllNotes() {

		return noteService.getAllNotes();
	}

}
