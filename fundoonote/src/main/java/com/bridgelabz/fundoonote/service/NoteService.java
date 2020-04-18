package com.bridgelabz.fundoonote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.dto.ReminderDto;
import com.bridgelabz.fundoonote.dto.TrashNotes;
import com.bridgelabz.fundoonote.dto.UpdateNote;
import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.exception.NoteException;

public interface NoteService {

	List<Noteinfo> getAllNotes();

	Noteinfo addNotes(NoteDto notes, String token);

	List<Noteinfo> updateNotes(String token, long id, UpdateNote updateDto);
	
	List<String> getNote(String id);

	List<Noteinfo> getNoteByUserId(String id);

	Noteinfo removeNotes(String token, long l);

	List<Noteinfo> sortByName();

	List<String> ascSortByName();

	Noteinfo archieveNote(long l, String token);

	Noteinfo pinNote(long id, String token);

	List<Noteinfo> getAlltrashednotes(String token);

	List<Noteinfo> getarchieved(String token);

	String addColour(String id, String token, String colour);

	List<Noteinfo> getAllPinneded(String token);

	String addReminder(long l, String token, ReminderDto reminder);

	String removeReminder(TrashNotes noteid, String token);

	Noteinfo unpinNote(long nid, String token);

	Noteinfo restoreNotes(String token, long nId);

	Noteinfo deleteNotes(String token, long nId);

	Noteinfo unarchieveNote(long nid, String token);

	Noteinfo getNotedetails(String id);
	

}