package com.bridgelabz.fundoo_note_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.service.CollabratorService;

@RestController
public class CollabratorController {
	@Autowired
	private CollabratorService service;

	@PostMapping("/collabrate/add")
	public ResponseEntity<Response> addCollabrator(@RequestParam("NoteId") long NoteId,
			@RequestParam("email") String email, @RequestHeader("token") String token) {
		      service.addCollabrator(NoteId, token, email);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collabrator added",200));
	}

	@GetMapping("/collabrate/getAllCollabrator")
	public ResponseEntity<Response> getAllCollabrator(@RequestHeader("token") String token) {
		      service.getAllCollabrator(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("Listed all collabrator information",200));
	}

	@DeleteMapping("/collabrate/deleteCollabrator")
	public ResponseEntity<Response> deleteCollabrator(@RequestParam("NoteId") long NoteId,
			@RequestParam("email") String email, @RequestHeader("token") String token) {
		     service.deleteCollabrator(NoteId, token, email);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collabrator removed", 200));
	}
}
