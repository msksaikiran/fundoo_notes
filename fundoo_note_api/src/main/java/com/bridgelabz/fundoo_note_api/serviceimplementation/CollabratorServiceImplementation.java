package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.UserNotFoundException;
import com.bridgelabz.fundoo_note_api.repository.NoteRepository;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.CollabratorService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@Service
public class CollabratorServiceImplementation implements CollabratorService {
	@Autowired
	private JwtGenerator generate;
//User user = new UserInformation();
	@Autowired
	private UserRepository repository;
	@Autowired
	private NoteRepository noteRepo;

	@Transactional
	@Override
	public Noteinfo addCollabrator(String NoteId, String token, String email) {

		User collabrator = repository.getUserByEmail(email);
		int noteId = Integer.parseInt(NoteId);
		User user;
		try {
			int id = (int) generate.parseJWT(token);
			user = repository.getUserById(id);
		} catch (Exception e) {
			throw new UserNotFoundException("User doesnot exist with this id");
		}
		if (user != null) {
			if (collabrator != null) {
				Noteinfo note = noteRepo.findNoteById(noteId);
				collabrator.getCollablare().add(note);
				return note;
			}

			else {
				throw new UserNotFoundException("user doesnot exist to collabrate");
			}
		} else {
			throw new UserNotFoundException("user not present");
		}

	}

	@Transactional
	@Override
	public List<User> getAllCollabrator(String token) {
		int id = (int) generate.parseJWT(token);
		List<User> user = repository.getCollobaraterById(id);
		//List<Noteinfo> note = user.getCollablare();
		return user;
	}

	@Transactional
	@Override
	public Noteinfo deleteCollabrator(String NoteId, String token, String email) {
		User collabrator = repository.getUserByEmail(email);
		int noteId = Integer.parseInt(NoteId);
		User user;
		try {
			int id = (int) generate.parseJWT(token);
			user = repository.getUserById(id);
		} catch (Exception e) {
			throw new UserNotFoundException("User doesnot exist with this id");
		}
		if (user != null) {
			if (collabrator != null) {
				Noteinfo note = noteRepo.findNoteById(noteId);
				collabrator.getCollablare().remove(note);
				return note;
			}

			else {
				throw new UserNotFoundException("user doesnot exist to collabrate");
			}
		} else {
			throw new UserNotFoundException("user not present");
		}

	}

}
