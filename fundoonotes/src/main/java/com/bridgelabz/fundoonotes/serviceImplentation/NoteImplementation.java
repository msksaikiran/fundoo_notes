package com.bridgelabz.fundoonotes.serviceImplentation;

import java.util.List;

import javax.transaction.Transactional;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ApplicationConfiguration;
import com.bridgelabz.fundoonotes.dto.Login;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.Noteinfo;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class NoteImplementation implements NoteService {
private Noteinfo note=new Noteinfo();
	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private JwtGenerator generate;
	
	@Transactional
	@Override
	public List<Noteinfo> getAllNotes(String id) {
		List<Noteinfo> notes = new ArrayList<>();   
		noteRepository.findAllById(Integer.parseInt(id)).forEach(notes::add);
		return notes;
	}

	@Transactional
	@Override
	public void addNotes(NoteDto notes) {

		//Long id=(Long)generate.parseJWT(token);
	    BeanUtils.copyProperties(notes,Noteinfo.class);
		Noteinfo result = noteRepository.save(note);
	}

	@Transactional
	@Override
	public Noteinfo getNote(String id) {
		Login userlogindto = new Login();
		List<Noteinfo> list = this.getAllNotes(id);

		for (Noteinfo ls : list) {
			if (ls.getNoteId() == Integer.parseInt(id)) {
				userlogindto.setEmail(ls.getTitle());
				userlogindto.setPassword(ls.getDescription());
				return ls;
			}
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