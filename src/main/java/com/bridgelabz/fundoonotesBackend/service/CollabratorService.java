package com.bridgelabz.fundoonotesBackend.service;

import java.util.List;

import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.entity.User;

public interface CollabratorService {
	
	
	List<Noteinfo> addCollabrator(long noteId, String token, String email);

	List<Noteinfo> getAllCollabrator(String token);

	Noteinfo deleteCollabrator(long noteId, long id);

}
