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
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.response.NoteResponse;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.service.NoteService;
import com.bridgelabz.fundoo_note_api.dto.ReminderDto;

@RestController
public class NotesController {
	@Autowired
	private NoteService noteService;

	/*
	 * API to add the Note Details
	 */
	@PostMapping(value = "/notes/users/{token}")
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

	/*
	 * API to Update the Note Details By Id
	 */
	@PutMapping(value = "/notes/{id}/users/{token}")
	public ResponseEntity<NoteResponse> updateNote(@PathVariable String token, @PathVariable String id,
			@RequestBody UpdateNote dto) {
		List<Noteinfo> note = noteService.updateNotes(token, id, dto);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Note Title Updated", dto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", dto));
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
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", result));
	}
	/*
	 * API to getting the Note Details By User_Id
	 */

	@GetMapping(value = "/notes/users/{token}")
	public ResponseEntity<NoteResponse> getNotesByUserId(@PathVariable String token) {
		List<Noteinfo> result = noteService.getNoteByUserId(token);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header(token, "sucess")
					.body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new NoteResponse("Not an existing user", result));
	}

	/*
	 * API to deleting the Note Details By _Id
	 */
	@DeleteMapping(value = "/notes/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable String noteId, @PathVariable String token) {
		Noteinfo result = noteService.removeNotes(token, noteId);
		if (result == null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new NoteResponse("Not existing user", result));
	}

	/*
	 * API for pin a Note
	 */
	@PutMapping("/notes/pin/{noteId}/users/{token}")
	public ResponseEntity<Response> pin(@PathVariable String noteId, @PathVariable String token) {
		noteService.pinNote(noteId, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note pined", 200));
	}

	/* API for archieve a Note */
	@PutMapping("/note/archieve/{noteId}/users/{token}")
	public ResponseEntity<Response> archieve(@PathVariable String noteId, @PathVariable String token) {
		noteService.archieveNote(noteId, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note archieved", 200));
	}

	/*
	 * API for updating color to a Note
	 */
	@PutMapping("/notes/{colour}/{noteId}/users/{token}")
	public ResponseEntity<Response> addColour(@PathVariable String noteId, @PathVariable String colour,
			@PathVariable String token) {
		noteService.addColour(noteId, token, colour);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note colour changed", 200));

	}

	/* API for getting all archieve notes Notes */
	@GetMapping("/notes/getAllArchieve/users/{token}")
	public ResponseEntity<Response> getArchieve(@PathVariable String token) {
		List<Noteinfo> list = noteService.getarchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" archieved notes", 200, list));
	}

	/*
	 * API for getting all trashed Notes
	 */
	@GetMapping("/notes/getAlltrashed/users/{token}")
	public ResponseEntity<Response> getTrashed(@PathVariable String token) {
		List<Noteinfo> list = noteService.getAlltrashednotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" trashed notes", 200, list));
	}

	/*
	 * API for getting all Pinned Notes
	 */
	@GetMapping("/notes/getAllPinned/users/{token}")
	public ResponseEntity<Response> getPinned(@PathVariable String token) {
		List<Noteinfo> list = noteService.getAllPinneded(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" pinned notes", 200, list));
	}

	/*
	 * API for adding remainder to Notes
	 */
	@PostMapping("/notes/addremainder/{noteId}/users/{token}")
	public ResponseEntity<Response> addRemainder(@PathVariable String token, @PathVariable String noteId,
			@RequestBody ReminderDto remainder) {
		noteService.addReminder(noteId, token, remainder);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(" reminderAdded notes", 200));
	}

	/*
	 * API for removing remainder Notes
	 */
	@DeleteMapping("/notes/removeRemainder/{noteId}/users/{token}")
	public ResponseEntity<Response> removeRemainder(@PathVariable String token, @PathVariable String noteId) {
		noteService.removeReminder(noteId, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Reminder notes Removed", 200));
	}
	/*
	 * API to get The All Note Details
	 */

	@GetMapping("/notes/user/notes")
	public List<Noteinfo> getAllNotes() {

		return noteService.getAllNotes();
	}

}
