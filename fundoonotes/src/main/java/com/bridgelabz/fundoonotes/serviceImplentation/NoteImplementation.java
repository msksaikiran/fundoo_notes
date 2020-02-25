package com.bridgelabz.fundoonotes.serviceImplentation;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ConfigurFile;
import com.bridgelabz.fundoonotes.dto.Userlogin;
import com.bridgelabz.fundoonotes.model.Noteinfo;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;

@Service
public class NoteImplementation implements NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	
	public List<Noteinfo> getAllNotes() {
		List<Noteinfo> notes = new ArrayList<>();
		
		noteRepository.findAll().forEach(notes::add);
		return notes;
	}

	public void addNotes(Noteinfo notes) {
		
		noteRepository.save(notes);
	}

	public Noteinfo getNotes(int id) {
		Userlogin userlogindto = new Userlogin();
		List<Noteinfo> list = this.getAllNotes();
		
		for (Noteinfo ls : list) {
			if (ls.getId() == id) {
				userlogindto.setEmail(ls.getTitle());
				userlogindto.setPassword(ls.getDescription());
				return ls;
			}
		}
		return null;

	}

	public void removeNotes(Noteinfo notes, String id) {
		List<Noteinfo> list = this.getAllNotes();
		for (Noteinfo ls : list) {
			
			if (ls.getId()==(Integer.parseInt(id))) {
				noteRepository.delete(ls);

			}
		}
	}

	

}