package com.bridgelabz.fundoonote.service;

import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.fundoonote.dto.LableDto;
import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.dto.UpdateLabel;
import com.bridgelabz.fundoonote.entity.Label;

public interface LabelService {

	List<Label> getAllLables();

	Label createLable(LableDto labelDto, String token);

	List<Label> removeLabel(String token, long id);

	List<Label> getLableByUserId(String token);
	
	Label getLableById(long id);

	//Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	ArrayList<String> sortByName();

	List<String> ascsortByName();

	Label addNotesToLabel(NoteDto notes, String token, long lid);

	boolean addExistingNotesToLabel(long noteId, String token, long labelId);

	Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	


}