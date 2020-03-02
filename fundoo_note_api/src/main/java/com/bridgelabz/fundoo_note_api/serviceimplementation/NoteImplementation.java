package com.bridgelabz.fundoo_note_api.serviceimplementation;

/*#
 * Description: implementation part for Note when user makes the request for add_notes,update,read,delete notes
 * @author : SaiKiranMsk
 *     
 */
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Label;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.NotesNotFoundException;
import com.bridgelabz.fundoo_note_api.exception.UserNotFoundException;
import com.bridgelabz.fundoo_note_api.repository.NoteRepository;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.NoteService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@Service
public class NoteImplementation implements NoteService {
	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JwtGenerator generate;

	@Transactional
	@Override
	public Noteinfo addNotes(NoteDto notes, String token) {

		try {
			Integer id = (Integer) generate.parseJWT(token);
			User user = userRepository.getUserById(id);
			if (user != null) {
				Noteinfo note = (Noteinfo) modelMapper.map(notes, Noteinfo.class);

				note.setColour("ash");
				note.setIsArchieved(0);
				note.setCreatedDateAndTime(LocalDateTime.now());
				note.setIsPinned(0);
				note.setIsTrashed(0);
				note.setReminder(null);
				note.setTitle(notes.getTitle());
				note.setDescription(notes.getDescription());
				note.setUser(user);
				return noteRepository.save(note);
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("user Not registered");
		}
		// BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}

	@Transactional
	@Override
	public List<Noteinfo> updateNotes(String id, UpdateNote updateDto) {

		List<Noteinfo> list = this.getAllNotes();
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			Optional<Noteinfo> data = list.stream().filter(t -> t.getNoteId() == nId).findFirst();
			data.ifPresent(da -> {
				da.setTitle(updateDto.getTitle());
				da.setCreatedDateAndTime(LocalDateTime.now());
				noteRepository.save(da);
			});
			if (data.equals(Optional.empty())){
				return null;
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("Label Record Not Exist");
		}
		return list;
	}

	@Transactional
	@Override
	public List<Noteinfo> getAllNotes() {
		List<Noteinfo> notes = new ArrayList<>();
		noteRepository.findAll().forEach(notes::add);
		return notes;
	}

	@Transactional
	@Override
	public Noteinfo removeNotes(String id) {

		List<Noteinfo> list = this.getAllNotes();
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			Optional<Noteinfo> data = list.stream().filter(t -> t.getNoteId() == nId).findFirst();
			data.ifPresent(da->{
				noteRepository.delete(da);
			});	
			if (data.equals(Optional.empty())){
				return null;
			}
		} catch (Exception ae) {
			throw new UserNotFoundException("user Not registered");
		}
		return list.get(0);
	}

	public List<Noteinfo> getNoteByUserId(String id) {
		List<Noteinfo> note = new ArrayList<>();
		try {
			noteRepository.findNoteByUserId(Integer.parseInt(id)).forEach(note::add);
			if (note != null) {
				return note;
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("user Not registered");
		}
		return null;
	}

	@Transactional
	@Override
	public Noteinfo getNote(String id) {
		/*
		 * using query
		 */
		Noteinfo notes = noteRepository.findNoteById(Integer.parseInt(id));
		if (notes != null) {
			return notes;
		}

		/*
		 * without using the query
		 */

		/*
		 * Login userlogindto = new Login(); List<Noteinfo> list =
		 * this.getAllNotes();
		 * 
		 * for (Noteinfo ls : list) { if (ls.getNoteId() ==
		 * Integer.parseInt(id)) { userlogindto.setEmail(ls.getTitle());
		 * userlogindto.setPassword(ls.getDescription()); return ls; } }
		 */
		return null;

	}
}