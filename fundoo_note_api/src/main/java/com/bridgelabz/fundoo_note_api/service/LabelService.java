package com.bridgelabz.fundoo_note_api.service;

import java.util.ArrayList;
import java.util.List;
import com.bridgelabz.fundoo_note_api.dto.LableDto;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateLabel;
import com.bridgelabz.fundoo_note_api.entity.Label;

public interface LabelService {

	List<Label> getAllLables();

	Label createLable(LableDto notes, String token);

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