package com.bridgelabz.fundoonote.serviceimplementation;

/*#
 * Description: implementation part for Label when user makes the request for add_label,update,read,delete label
 * @author : SaiKiranMsk
 *     
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.dto.LableDto;
import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.entity.Label;
import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.entity.User;
import com.bridgelabz.fundoonote.exception.LabelException;
import com.bridgelabz.fundoonote.exception.UserException;
import com.bridgelabz.fundoonote.repository.LabelRepository;
import com.bridgelabz.fundoonote.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.LabelService;
import com.bridgelabz.fundoonote.service.NoteService;
import com.bridgelabz.fundoonote.utility.JwtGenerator;

@Service
@PropertySource("classpath:message.properties")
public class LabelImplementation implements LabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private JwtGenerator generate;

	@Autowired
	private Environment env;

	@Transactional
	@Override
	public Label createLable(LableDto labelDto, String token) {
		
		long userId = (long) generate.parseJWT(token);
            
		Optional<Label> labeldetails = labelRepository.findLableByName(labelDto.getLableName());
		             // orElseThrow(() -> new LabelException(HttpStatus.BAD_GATEWAY,"label Name Exist"));
		if(labeldetails.isPresent()) {
			throw new LabelException(HttpStatus.BAD_GATEWAY,"label Name already Exist");
		}
		
		Label label=new Label();
		User user = userRepository.getUserById(userId)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		BeanUtils.copyProperties(labelDto,label);
		user.getLabel().add(label);
		return labelRepository.save(label);
		

	}

	@Transactional
	@Override
	public Label addLabelToNotes(long nId,String lname,String token) {

		
         Noteinfo note = noteRepository.findNoteById(nId)
        		 .orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));
             
		Label lables = labelRepository.findLableByName(lname)
				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));
		
		try {

		    note.getLabel().add(lables);
			

		} catch (Exception ae) {
			ae.printStackTrace();
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Notes not added to Label due to Internel server problem");
		}

		return lables;

	}

	@Transactional
	@Override
	public Label removeLabelToNotes(long nId,String lId,String token) {

		
        Noteinfo note = noteRepository.findNoteById(nId)
       		 .orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));
            
		Label lables = labelRepository.findLableByName(lId)
				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));
		
		try {

			note.getLabel().remove(lables);
			

		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Notes not added to Label due to Internel server problem");
		}

		return lables;

	}
	
	@Override
	public ArrayList<Noteinfo> LabelNote(long lId) {
		ArrayList<Noteinfo> list=new ArrayList<>();
		List<Long> labelNote = labelRepository.findLabelNote(lId);
				//.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));
		for(long lb:labelNote) {
			list.add(noteRepository.findNoteById(lb).orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104"))));
		}
		return list;
	}
	
	@Transactional
	@Override
	public Label updateLabel(String token, long lId, LableDto LabelDto) {

		long userId = (long) generate.parseJWT(token);
		
		// List<Label> list = new ArrayList<Label>();
		List<Label> list = labelRepository.findLableByUserId(userId);
		
		if (list.isEmpty())
			return null;

		/*
		 * java 8 streams feature
		 */
		Label labelData = list.stream().filter(t -> t.getlId()==lId).findFirst()
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));

			labelData.setLableName(LabelDto.getLableName());
			labelData.setUpdateDateAndTime(LocalDateTime.now());
			labelRepository.save(labelData);


		return labelData;
	}

	@Transactional
	@Override
	public Label removeLabel(String token, long lId) {

		long userId = (long) generate.parseJWT(token);

		  List<Noteinfo> notes = noteRepository.findNoteByUserId(userId);
				  
			Label lables = labelRepository.findLableById(lId)
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));
			
			try {

				for(Noteinfo note:notes) {
				note.getLabel().remove(lables);
				}
				
				labelRepository.delete(lables); 
			} catch (Exception ae) {
				ae.printStackTrace();
				throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Notes not added to Label due to Internel server problem");
			}

			return lables;
		

	}

	
	@Override
	public List<String> ascsortByName() {
		ArrayList<String> lis = new ArrayList<>();
		List<Label> list = this.getAllLables();
		/*
		 * java 8 lambda feature for sorting
		 */
		list.forEach(t -> {
			lis.add(t.getLableName());

		});
		Collections.sort(lis);
		return lis;
	}

	@Transactional
	@Override
	public ArrayList<String> sortByName() {
		ArrayList<String> lis = new ArrayList<>();
		List<Label> list = this.getAllLables();
		/*
		 * java 8 lambda feature for sorting
		 */
		list.forEach(t -> {
			lis.add(t.getLableName());

		});

		Collections.sort(lis, Collections.reverseOrder());
		return lis;
	}

	@Transactional
	@Override
	public List<Label> getAllLables() {
		List<Label> notes = new ArrayList<>();

		labelRepository.findAll().forEach(notes::add);
		return notes;
	}

	@Transactional
	@Override
	public List<String> getLableByUserId(String token) {
		
		long id = (long) generate.parseJWT(token);
		List<String> labelnames=new ArrayList<>();
		List<Label> label = labelRepository.findLableByUserId(id);
		for(Label labelinfo:label) {
			labelnames.add(labelinfo.getLableName());
		}
		
			return labelnames;
		
	}

	@Transactional
	@Override
	public Label getLableById(long id) {
		/*
		 * using query
		 */
		Label label = labelRepository.findLableById(id)
				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));
		if (label != null) {
			return label;
		}
		return null;

	}

	@Override
	public boolean addExistingNotesToLabel(String noteTitle, String token, String labelName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Label> getLableDetailsByUserId(String token) {
		long id = (long) generate.parseJWT(token);
		
		List<Label> label = labelRepository.findLableByUserId(id);
			return label;
	}

	

	

}
