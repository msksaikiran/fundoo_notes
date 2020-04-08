package com.bridgelabz.fundoonote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.bridgelabz.fundoonote.dto.LableDto;
import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.dto.UpdateLabel;
import com.bridgelabz.fundoonote.entity.Label;
import com.bridgelabz.fundoonote.entity.Noteinfo;

public interface LabelService {

	List<Label> getAllLables();

	Label createLable(LableDto labelDto, String token);

	Label removeLabel(String token, long id);

	Set<Label> getLableByUserId(String token);
	
	Label getLableById(long id);

	//Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	ArrayList<String> sortByName();

	List<String> ascsortByName();

	Label addLabelToNotes(long nId,  long lId,String token);

	boolean addExistingNotesToLabel(String noteTitle, String token, String labelName);

	Label updateLabel(String token, long lId, UpdateLabel LabelDto);

	ArrayList<Noteinfo> LabelNote(long lId);

	Label removeLabelToNotes(long nId, long lId, String token);
	
	
}