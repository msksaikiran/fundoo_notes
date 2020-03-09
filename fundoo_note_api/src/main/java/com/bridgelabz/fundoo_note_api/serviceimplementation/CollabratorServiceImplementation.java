package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.LabelException;
import com.bridgelabz.fundoo_note_api.exception.UserException;
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
	public List<Noteinfo> addCollabrator(long noteId, String token, String email) {

		Optional<User> collabrator = repository.findUserByEmail(email);

		if (collabrator.isPresent()) {
			try {
				long uid = (long) generate.parseJWT(token);
				System.out.println("UserId:" + uid);

				List<Noteinfo> note = noteRepo.findNoteByUserId(uid);
				if (note != null) {
					Noteinfo data = note.stream().filter(t -> t.getNid() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

					collabrator.ifPresent(t -> t.getCollablare().add(data));

					System.out.println("da::" + data);

					return note;
				}
			} catch (Exception e) {
				// throw new UserException("User doesnot exist with this id");
			}

		}
		return null;

	}

	@Transactional
	@Override
	public List<User> getAllCollabrator(String token) {
		long id = (long) generate.parseJWT(token);
		List<User> user = repository.getCollobaraterById(id);

		if (user == null) {
			throw new UserException(HttpStatus.BAD_GATEWAY, "user not exist");
		}
		return user;
	}

	@Transactional
	@Override
	public Noteinfo deleteCollabrator(long noteId, String token, String email) {
		Optional<User> collabrator = repository.findUserByEmail(email);
		// User user=null;
		if (collabrator.isPresent()) {
			try {
				long uid = (long) generate.parseJWT(token);
				System.out.println("UserId:" + uid);

				List<Noteinfo> note = noteRepo.findNoteByUserId(uid);
				if (note != null) {
					Noteinfo data = note.stream().filter(t -> t.getNid() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

					collabrator.ifPresent(t -> t.getCollablare().remove(note));

					System.out.println("da::" + data);

					return data;
				}
			} catch (Exception e) {
				// throw new UserException("User doesnot exist with this id");
			}

		}
		return null;
	}

}
