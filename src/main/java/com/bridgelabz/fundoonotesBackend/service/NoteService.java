package com.bridgelabz.fundoonotesBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesBackend.dto.NoteDto;
import com.bridgelabz.fundoonotesBackend.dto.ReminderDto;
import com.bridgelabz.fundoonotesBackend.dto.TrashNotes;
import com.bridgelabz.fundoonotesBackend.dto.UpdateNote;
import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.entity.User;
import com.bridgelabz.fundoonotesBackend.exception.NoteException;

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
	
	void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,long token);

	void deleteFileFromS3Bucket(String fileName,long token);
	
	ArrayList<String> getImageUrl(long nid);
	

}