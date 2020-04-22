package com.bridgelabz.fundoonotesBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bridgelabz.fundoonotesBackend.dto.LableDto;
import com.bridgelabz.fundoonotesBackend.dto.NoteDto;
import com.bridgelabz.fundoonotesBackend.entity.Label;
import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;

public interface LabelService {

	List<Label> getAllLables();

	Label createLable(LableDto labelDto, String token);

	Label removeLabel(String token, long labelid);

	List<String> getLableByUserId(String token);
	
	Label getLableById(long id);

	//Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	ArrayList<String> sortByName();

	List<String> ascsortByName();

	Label addLabelToNotes(long nId, String lname, String token);

	boolean addExistingNotesToLabel(String noteTitle, String token, String labelName);

	Label updateLabel(String token, long lId, LableDto labelDto);

	ArrayList<Noteinfo> LabelNote(long lId);

	Label removeLabelToNotes(long nId, String string, String token);

	//Label getLableDetailsById(String id);

	List<Label> getLableDetailsByUserId(String token);

	

	
	
	
}