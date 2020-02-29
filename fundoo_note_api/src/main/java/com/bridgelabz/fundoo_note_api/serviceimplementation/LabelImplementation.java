package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo_note_api.dto.LableDto;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.entity.Label;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.repository.LabelRepository;
import com.bridgelabz.fundoo_note_api.repository.NoteRepository;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.LabelService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@Service
public class LabelImplementation implements LabelService{

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JwtGenerator generate;
	

	@Transactional
	@Override
	public Label createLable(LableDto labelDto,String token) {
		int userId = (Integer)generate.parseJWT(token);
		System.out.println(userId);
	     User user = userRepository.getUserById(userId);
		if(user!=null){
			Label label = (Label)modelMapper.map(labelDto,Label.class);
			label.setUserId(userId);
			//label.setNote(noteRepository.findNoteByUserId(userId));
			//label.set
			return labelRepository.save(label);
		}
	    //BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}

	@Transactional
	@Override
	public List<Label> getAllLables(){
		List<Label> notes = new ArrayList<>();  
		
		labelRepository.findAll().forEach(notes::add);
		//notes.add(noteRepository.findNoteByUserId(parseInt))
		return notes;
	}
		
	@Transactional
	@Override
	public Label removeLabel(String id) {
		
//		/*
//		 * using query
//		 */
//		return labelRepository.removeLabel(Integer.parseInt(id));
		
		/*
		 * without using the query
		 */
		List<Label> list = this.getAllLables();
		//Integer id=(Integer)generate.parseJWT(token);
		for (Label ls : list) {
			if (ls.getLableId() == (Integer.parseInt(id))) {
				labelRepository.delete(ls);
                return ls;
			}
		}
		return null;
	}
	
	@Transactional
	@Override
	public List<Label> getLableByUserId(String id) {
		List<Label> note = new ArrayList<>();
	    labelRepository.findLableByUserId(Integer.parseInt(id)).forEach(note::add);
		if(note!=null) {
			return note;
		}
		return null;
	}
	
	@Transactional
	@Override
	public Label getLableById(String id) {
		/*
		 * using query
		 */
		Label label = labelRepository.findLableById(Integer.parseInt(id));
		if(label!=null) {
			return label;
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


