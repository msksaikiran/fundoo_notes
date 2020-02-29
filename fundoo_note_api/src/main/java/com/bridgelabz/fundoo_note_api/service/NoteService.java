package com.bridgelabz.fundoo_note_api.service;

import java.util.List;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;

public interface NoteService {

	List<Noteinfo> getAllNotes();

	Noteinfo addNotes(NoteDto notes, String token);
	
	Noteinfo updateNotes(String id, UpdateNote dto);
	
    Noteinfo getNote(String id);

    List<Noteinfo> getNoteByUserId(String id);
    
	Noteinfo removeNotes(String id);

}