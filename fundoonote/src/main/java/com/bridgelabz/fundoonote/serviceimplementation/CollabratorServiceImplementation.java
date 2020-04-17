package com.bridgelabz.fundoonote.serviceimplementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.entity.User;
import com.bridgelabz.fundoonote.exception.LabelException;
import com.bridgelabz.fundoonote.exception.UserException;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.CollabratorService;
import com.bridgelabz.fundoonote.utility.JwtGenerator;

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

		User collabrator = repository.findUserByEmail(email)
				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "User not Exist"));
		
				long uid = (long) generate.parseJWT(token);
				System.out.println("UserId:" + uid);

				List<Noteinfo> note = noteRepo.findNoteByUserId(uid);
				if (note != null) {
					Noteinfo data = note.stream().filter(t -> t.getNid() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

                    collabrator.getCollablare().add(data);
					
				}

				return note;

	}

	@Transactional
	@Override
	public List<Noteinfo> getAllCollabrator(String token) {
		long id = (long) generate.parseJWT(token);
		//List<User> user = repository.getCollobaraterById(id);
		User user = repository.getUserById(id)
				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

		return user.getCollablare();
		
	}

	@Transactional
	@Override
	public Noteinfo deleteCollabrator(long noteId, String token) {
		long uid = (long) generate.parseJWT(token);
		Optional<User> collabrator = repository.findById(uid);
		if (collabrator.isPresent()) {
			try {
				
				System.out.println("UserId:" + uid);

				List<Noteinfo> note = noteRepo.findNoteByUserId(uid);
				if (note != null) {
					Noteinfo data = note.stream().filter(t -> t.getNid() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

					collabrator.ifPresent(t -> t.getCollablare().remove(data));

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
