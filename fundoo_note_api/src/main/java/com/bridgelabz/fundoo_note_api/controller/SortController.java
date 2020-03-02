package com.bridgelabz.fundoo_note_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo_note_api.response.NoteResponse;
import com.bridgelabz.fundoo_note_api.service.LabelService;
import com.bridgelabz.fundoo_note_api.service.NoteService;

@RestController
public class SortController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private LabelService labelService;

	/*
	 * API to Sort the Note Details By Title
	 */

	@GetMapping(value = "/notes/sortByTitle")
	public ResponseEntity<NoteResponse> sortByNoteTitle() {
		List<String> result = noteService.sortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", result));
	}

	/*
	 * API to Sort the Label Details By Name
	 */

	@GetMapping(value = "/label/sortByName")
	public ResponseEntity<NoteResponse> sortByLabelName() {
		List<String> result = labelService.sortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", result));
	}

}
