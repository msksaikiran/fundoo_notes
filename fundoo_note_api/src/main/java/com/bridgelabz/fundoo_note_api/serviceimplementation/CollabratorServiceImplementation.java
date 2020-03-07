package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
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

//	@Transactional
//	@Override
//	public List<Noteinfo> addCollabrator(String NoteId, String token, String email) {
//
//		Optional<User> collabrator = repository.findUserByEmail(email);
//		int noteId = Integer.parseInt(NoteId);
//		System.out.println("NoteId:" + noteId);
//		User user;
//		if (collabrator != null) {
//			try {
//				int uid = (int) generate.parseJWT(token);
//				System.out.println("UserId:" + uid);
//
//				List<Noteinfo> note = noteRepo.findNoteByUserId(uid);
//				if (note != null) {
//					Optional<Noteinfo> data = note.stream().filter(t -> t.getNoteId() == noteId).findFirst();
//					System.out.println(data);
//					data.ifPresent(da -> {
//						da.getUser();
//						//collabrator.getCollablare().add(da);
//						
//						System.out.println("da::"+da);
//					});
//
//					// collabrator.setCollablare(note);
//					return note;
//				} else {
//					throw new UserException("user doesnot exist to collabrate");
//				}
//			} catch (Exception e) {
//				throw new UserException("User doesnot exist with this id");
//			}
//
//		} else {
//			throw new UserException("user not present");
//		}
//
//	}

	@Transactional
	@Override
	public User getAllCollabrator(String token) {
		long id = (long) generate.parseJWT(token);
		User user = repository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user not exist"));;
		// Object note = user.getCollablare();
		return user;
	}

@Override
public List<Noteinfo> addCollabrator(String noteId, String token, String email) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Noteinfo deleteCollabrator(String NoteId, String token, String email) {
	// TODO Auto-generated method stub
	return null;
}

//	@Transactional
//	@Override
//	public Noteinfo deleteCollabrator(String NoteId, String token, String email) {
//		Optional<User> collabrator = repository.findUserByEmail(email);
//		int noteId = Integer.parseInt(NoteId);
//		User user;
//		try {
//			int id = (int) generate.parseJWT(token);
//			user = repository.getUserById(id);
//		} catch (Exception e) {
//			//throw new UserException("User doesnot exist with this id");
//		}
//		if (user != null) {
//			if (collabrator != null) {
//				Noteinfo note = noteRepo.findNoteById(noteId);
//				//collabrator.getCollablare().remove(note);
//				return note;
//			}
//
//			else {
//				//throw new UserException("user doesnot exist to collabrate");
//			}
//		} else {
//			//throw new UserException("user not present");
//		}
//
//	}

}
