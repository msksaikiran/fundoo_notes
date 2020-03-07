package com.bridgelabz.fundoo_note_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.ReminderDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.exception.NoteException;

public interface NoteService {

	List<Noteinfo> getAllNotes();

	Noteinfo addNotes(NoteDto notes, String token);

	List<Noteinfo> updateNotes(String token, String id, UpdateNote updateDto);
	
	Noteinfo getNote(String id);

	List<Noteinfo> getNoteByUserId(String id);

	Noteinfo removeNotes(String token, String id);

	ArrayList<String> sortByName();

	List<String> ascSortByName();

	String archieveNote(String id, String token);

	Noteinfo pinNote(String id, String token);

	List<Noteinfo> getAlltrashednotes(String token);

	List<Noteinfo> getarchieved(String token);

	String addColour(String id, String token, String colour);

	List<Noteinfo> getAllPinneded(String token);

	String addReminder(String id, String token, ReminderDto reminder);

	String removeReminder(String id, String token);
	

}