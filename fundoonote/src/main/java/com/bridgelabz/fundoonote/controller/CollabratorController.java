package com.bridgelabz.fundoonote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.response.NoteResponse;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.service.CollabratorService;

@RestController
@RequestMapping("/collabrate")
public class CollabratorController {
	
	@Autowired
	private CollabratorService service;

	@PostMapping("/add-coll/{token}")
	public ResponseEntity<Response> addCollabrator(@RequestParam("NoteId") long NoteId,
			@RequestParam("email") String email, @PathVariable String token) {
		      service.addCollabrator(NoteId, token, email);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collabrator added",200));
	}

	@GetMapping("/getAll/{token}")
	public ResponseEntity<NoteResponse> getAllCollabrator(@PathVariable String token) {
		      List<Noteinfo> collabare = service.getAllCollabrator(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new NoteResponse("Listed all collabrator information",collabare,200));
	}

	@DeleteMapping("/delete-coll")
	public ResponseEntity<Response> deleteCollabrator(@RequestParam("NoteId") long NoteId,
			@RequestParam("email") String email) {
		     service.deleteCollabrator(NoteId, email);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collabrator removed", 200));
	}
}
