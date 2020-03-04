package com.bridgelabz.fundoo_note_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.service.CollabratorService;



@RestController
public class CollabratorController {
 @Autowired
private CollabratorService service;
 
 @PostMapping("Collabrate/add")
 public ResponseEntity<Response> addCollabrator(@RequestParam("NoteId") String NoteId,@RequestParam("email")String email,@RequestHeader("token") String token){
	Noteinfo note =service.addCollabrator(NoteId, token, email);
	return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collabrator added", 200,note));
 }
	@GetMapping("Collabrate/getAllCollabrator")
	public ResponseEntity<Response> getAllCollabrator(@RequestHeader("token") String token){
	List<User> user = service.getAllCollabrator(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body( new Response("Listed all collabrator information", 200,user));
	}
	@DeleteMapping("collabrate/deleteCollabrator")
	public ResponseEntity<Response> deleteCollabrator(@RequestParam("NoteId") String NoteId,@RequestParam("email")String email,@RequestHeader("token") String token){
	Noteinfo note = service.deleteCollabrator(NoteId, token, email);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collabrator removed", 200));
	}
}
