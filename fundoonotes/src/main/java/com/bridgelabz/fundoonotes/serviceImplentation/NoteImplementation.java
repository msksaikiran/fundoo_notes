package com.bridgelabz.fundoonotes.serviceImplentation;

import java.util.List;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ApplicationConfiguration;
import com.bridgelabz.fundoonotes.dto.Login;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.Noteinfo;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class NoteImplementation implements NoteService {
//private Noteinfo note=new Noteinfo();
	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtGenerator generate;
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	@Override
	public List<Noteinfo> getAllNotes(String id) {
		List<Noteinfo> notes = new ArrayList<>();   
		noteRepository.findAllById(Integer.parseInt(id)).forEach(notes::add);
		return notes;
	}

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
			return noteRepository.save(note);
		}
	    //BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}

	@Transactional
	@Override
	public Noteinfo getNote(String token) {
		Login userlogindto = new Login();
		List<Noteinfo> list = this.getAllNotes(token);
		Noteinfo note =null;
		for (Noteinfo ls : list) {
			if (ls.getNoteId() == Integer.parseInt(token)) {
				userlogindto.setEmail(ls.getTitle());
				userlogindto.setPassword(ls.getDescription());
				note= noteRepository.getNote(Integer.parseInt(token));
				
			}
		}
		if(note!=null){
			return note;
		}
		return null;
	}

	@Transactional
	@Override
	public void removeNotes(Noteinfo notes, String id) {
		List<Noteinfo> list = this.getAllNotes(id);
		for (Noteinfo ls : list) {
			if (ls.getNoteId() == (Integer.parseInt(id))) {
				noteRepository.delete(ls);

			}
		}
	}


}