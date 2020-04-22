package com.bridgelabz.fundoonotesBackend.serviceimplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.entity.User;
import com.bridgelabz.fundoonotesBackend.exception.LabelException;
import com.bridgelabz.fundoonotesBackend.exception.UserException;
import com.bridgelabz.fundoonotesBackend.repository.NoteRepository;
import com.bridgelabz.fundoonotesBackend.repository.UserRepository;
import com.bridgelabz.fundoonotesBackend.service.CollabratorService;
import com.bridgelabz.fundoonotesBackend.utility.JwtGenerator;

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
				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "User not Exist"));

		return user.getCollablare();
		
	}

	@Transactional
	@Override
	public Noteinfo deleteCollabrator(long noteId, long id) {
		
		User coluser= repository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user Not exist"));
	    
		Noteinfo data = coluser.getCollablare().stream().filter(t -> t.getNid() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

        coluser.getCollablare().remove(data);
					
				
				return data;
	}

}
