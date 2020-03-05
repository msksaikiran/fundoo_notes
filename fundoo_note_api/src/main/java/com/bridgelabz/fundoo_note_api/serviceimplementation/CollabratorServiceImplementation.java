package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.util.List;
import java.util.Optional;

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
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private NoteRepository noteRepo;

	@Transactional
	@Override
	public List<Noteinfo> addCollabrator(String NoteId, String token, String email) {

		User collabrator = repository.getUserByEmail(email);
		int noteId = Integer.parseInt(NoteId);
		System.out.println("NoteId:" + noteId);
		User user;
		if (collabrator != null) {
			try {
				int uid = (int) generate.parseJWT(token);
				System.out.println("UserId:" + uid);

				List<Noteinfo> note = noteRepo.findNoteByUserId(uid);
				if (note != null) {
					Optional<Noteinfo> data = note.stream().filter(t -> t.getNoteId() == noteId).findFirst();
					System.out.println(data);
					data.ifPresent(da -> {
						
						collabrator.getCollablare().add(da);
						
						System.out.println("da::"+da);
					});

					// collabrator.setCollablare(note);
					return note;
				} else {
					throw new UserNotFoundException("user doesnot exist to collabrate");
				}
			} catch (Exception e) {
				throw new UserNotFoundException("User doesnot exist with this id");
			}

		} else {
			throw new UserNotFoundException("user not present");
		}

	}

	@Transactional
	@Override
	public User getAllCollabrator(String token) {
		int id = (int) generate.parseJWT(token);
		User user = repository.getUserById(id);
		// Object note = user.getCollablare();
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
