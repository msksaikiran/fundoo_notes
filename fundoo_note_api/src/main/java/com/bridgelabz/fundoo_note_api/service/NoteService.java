package com.bridgelabz.fundoo_note_api.service;

import java.util.List;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;

public interface NoteService {

	List<Noteinfo> getAllNotes();

	Noteinfo addNotes(NoteDto notes, String token);
	
    Noteinfo getNote(String id);

	Noteinfo removeNotes(String id);

    List<Noteinfo> getNoteByUserId(String id);

	

}