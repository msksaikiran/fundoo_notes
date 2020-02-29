package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.util.List;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.NotesNotFoundException;
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
	public Noteinfo addNotes(NoteDto notes,String token) {

		Integer id=(Integer)generate.parseJWT(token);
		User user = userRepository.getUserById(id);
		if(user!=null){
			Noteinfo note = (Noteinfo)modelMapper.map(notes, Noteinfo.class);
			
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
	    //BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}

	@Transactional
	@Override
	public Noteinfo updateNotes(String id,UpdateNote updateDto) {
		
		List<Noteinfo> list = this.getAllNotes();
		/*
		 * java 8 streams feature
		 */
		list.stream().filter(t->t.getNoteId()==(Integer.parseInt(id))).forEach(t->{
			
			Noteinfo note = (Noteinfo)modelMapper.map(updateDto, Noteinfo.class);
	    	noteRepository.save(note);	
			
	    });
		return null;
		//throw new NotesNotFoundException("User Not Exist");
	}
	
	@Transactional
	@Override
	public List<Noteinfo> getAllNotes(){
		List<Noteinfo> notes = new ArrayList<>();  
		noteRepository.findAll().forEach(notes::add);
		//notes.stream().forEach(data->System.out.println(data));
		//noteRepository.findAll().forEach(notes::add);
		return notes;
	}
	
	@Transactional
	@Override
	public Noteinfo removeNotes(String id) {
		
		List<Noteinfo> list = this.getAllNotes();
		/*
		 * java 8 streams feature
		 */
		list.stream().filter(t->t.getNoteId()==(Integer.parseInt(id))).forEach(t->{
	    	noteRepository.delete(t);	
			return;
	    });
//		for (Noteinfo ls : list) {
//			if (ls.getNoteId() == (Integer.parseInt(id))) {
//				noteRepository.delete(ls);
//                return ls;
//			}
//		}
		return null;  
	}
	
	public List<Noteinfo> getNoteByUserId(String id) {
		List<Noteinfo> note = new ArrayList<>();
	    noteRepository.findNoteByUserId(Integer.parseInt(id)).forEach(note::add);
	  
		if(note!=null) {
			return note;
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
		if(notes!=null) {
			return notes;
		}

		/*
		 * without using the query
		 */
		
  /*    Login userlogindto = new Login();
		List<Noteinfo> list = this.getAllNotes();

		for (Noteinfo ls : list) {
			if (ls.getNoteId() == Integer.parseInt(id)) {
				userlogindto.setEmail(ls.getTitle());
				userlogindto.setPassword(ls.getDescription());
				return ls;
			}
		}       */
		return null;

	}	
}