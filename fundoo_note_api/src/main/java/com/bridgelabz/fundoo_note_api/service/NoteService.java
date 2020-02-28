package com.bridgelabz.fundoo_note_api.service;

import java.util.List;

import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;

public interface NoteService {

	public List<Noteinfo> getAllNotes();

	Noteinfo addNotes(NoteDto notes, String token);
	
	public Noteinfo getNote(String id);

	public void removeNotes(Noteinfo notes, String id);

	public List<Noteinfo> getNoteByUserId(String id);

}