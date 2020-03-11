package com.bridgelabz.fundoonote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.response.LabelResponse;
import com.bridgelabz.fundoonote.response.NoteResponse;
import com.bridgelabz.fundoonote.service.LabelService;
import com.bridgelabz.fundoonote.service.NoteService;

@RestController
public class SortController {

	@Autowired
	private NoteService noteService;

	@Autowired
	private LabelService labelService;

	/*
	 * API to Sort the Note Details By Title in ascending order
	 */

	@GetMapping(value = "/notes/ascendingSortByTitle")
	public ResponseEntity<NoteResponse> ascSortByNoteTitle() {
		List<String> result = noteService.ascSortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Sorted sucessfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist"));
	}
	
	/*
	 * API to Sort the Note Details By Title in descending order
	 */

	@GetMapping(value = "/notes/descSortByTitle")
	public ResponseEntity<NoteResponse> descSortByNoteTitle() {
		List<String> result = noteService.sortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Sorted sucessfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist"));
	}
	
	/*
	 * API to Sort the Label Details By Name
	 */

	@GetMapping(value = "/label/ascendingSortByName")
	public ResponseEntity<LabelResponse> ascSortByLabelName() {
		List<String> result = labelService.ascsortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponse("sorted sucessfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabelResponse("Note Not Exist"));
	}

	

	/*
	 * API to Sort the Label Details By Name
	 */

	@GetMapping(value = "/label/descSortByName")
	public ResponseEntity<LabelResponse> descSortByLabelName() {
		List<String> result = labelService.sortByName();
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LabelResponse("sorted sucessfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabelResponse("Note Not Exist"));
	}

}
