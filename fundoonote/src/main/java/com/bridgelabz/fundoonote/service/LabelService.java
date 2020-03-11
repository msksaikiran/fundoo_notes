package com.bridgelabz.fundoonote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bridgelabz.fundoonote.dto.LableDto;
import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.dto.UpdateLabel;
import com.bridgelabz.fundoonote.entity.Label;

public interface LabelService {

	List<Label> getAllLables();

	Label createLable(LableDto labelDto, String token);

	Label removeLabel(String token, long id);

	Set<Label> getLableByUserId(String token);
	
	Label getLableById(long id);

	//Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	ArrayList<String> sortByName();

	List<String> ascsortByName();

	Label addNotesToLabel(NoteDto notes, String token, long lid);

	boolean addExistingNotesToLabel(String noteTitle, String token, String labelName);

	Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	boolean addExistingNotesToLabel(long noteId, String token, long labelId);

	

	

	


}