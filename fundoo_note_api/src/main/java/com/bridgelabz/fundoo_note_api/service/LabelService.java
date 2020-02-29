package com.bridgelabz.fundoo_note_api.service;

import java.util.List;
import com.bridgelabz.fundoo_note_api.dto.LableDto;
import com.bridgelabz.fundoo_note_api.entity.Label;

public interface LabelService {

	List<Label> getAllLables();

	Label createLable(LableDto notes, String token);

	Label removeLabel(String id);

    List<Label> getLableByUserId(String id);

	Label getLableById(String id);


	

	

}