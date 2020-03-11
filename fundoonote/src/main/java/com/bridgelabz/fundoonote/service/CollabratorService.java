package com.bridgelabz.fundoonote.service;

import java.util.List;

import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.entity.User;

public interface CollabratorService {
	
	
	List<Noteinfo> addCollabrator(long noteId, String token, String email);

	List<Noteinfo> getAllCollabrator(String token);

	Noteinfo deleteCollabrator(long noteId, String token);

}
