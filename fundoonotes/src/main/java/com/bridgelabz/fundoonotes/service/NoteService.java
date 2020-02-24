package com.bridgelabz.fundoonotes.service;


import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ConfigurFile;
import com.bridgelabz.fundoonotes.dto.Userlogin;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;
	@Autowired
    private ConfigurFile config;
	
	public List<UserRecord> getAllNotes() {
		List<UserRecord> notes = new ArrayList<>();
		//userRecords.setPassword(config.passwordEncoder().(userRecords.getPassword()));
		//config.passwordEncoder().matches(userRecords.get("pass"), encodedPassword)
		noteRepository.findAll().forEach(notes::add);
		return notes;
	}

	public void addNotes(UserRecord notes) {
		notes.setPassword(config.passwordEncoder().encode(notes.getPassword()));
		noteRepository.save(notes);
	}

	public UserRecord getNotes(int id) {
		Userlogin userlogindto = new Userlogin();
		List<UserRecord> list = this.getAllNotes();
		
		for (UserRecord ls : list) {
			if (ls.getId() == id) {
				userlogindto.setEmail(ls.getEmail());
				userlogindto.setPassword(ls.getName());
				return ls;
			}
		}
		return null;

	}

	public void removeNotes(UserRecord userRecord, String name) {
		List<UserRecord> list = this.getAllNotes();
		for (UserRecord ls : list) {
			if (ls.getName().equalsIgnoreCase(name)) {
				noteRepository.delete(ls);

			}
		}
	}

}