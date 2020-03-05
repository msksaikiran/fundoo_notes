package com.bridgelabz.fundoo_note_api.service;

import java.util.List;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;

public interface CollabratorService {
	
	
	List<Noteinfo> addCollabrator(String noteId, String token, String email);

	User getAllCollabrator(String token);

	Noteinfo deleteCollabrator(String NoteId, String token, String email);

}
