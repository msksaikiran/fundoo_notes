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
import com.bridgelabz.fundoonote.dto.UpdateLabel;
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
	private NoteService noteService;

	@Autowired
	private Environment env;

	@Transactional
	@Override
	public Label createLable(LableDto labelDto, String token) {
		
		long userId = (long) generate.parseJWT(token);

		Label label=new Label();
		User user = userRepository.getUserById(userId)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		BeanUtils.copyProperties(labelDto, label);
		 user.getLabel().add(label);
		//note.getLabel().add(label);
		return labelRepository.save(label);

	}

	@Transactional
	@Override
	public Label addNotesToLabel(NoteDto notes, String token, long lId) {

		Noteinfo note = noteService.addNotes(notes, token);

		Set<Label> lables = this.getLableByUserId(token);
		Label labelInfo;
		try {
			labelInfo = lables.stream().filter(t -> t.getLId() == lId).findFirst()
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));

			this.addExistingNotesToLabel(note.getNid(), token, lId);
			//labelInfo.getNote().add(note);
			// note.getLabel().add(labelInfo);

		} catch (Exception ae) {
			ae.printStackTrace();
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Notes not added to Label due to Internel server problem");
		}

		return labelInfo;

	}

	@Transactional
	@Override
	public boolean addExistingNotesToLabel(long noteId, String token, long labelId) {
		long userId = (long) generate.parseJWT(token);

		List<Noteinfo> notes = noteRepository.findNoteByUserId(userId);

		Label label = labelRepository.findLableById(labelId);

		
			Noteinfo noteInfo = notes.stream().filter(t -> t.getNid() == noteId).findFirst()
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));
		try {
			return noteInfo.getLabel().add(label);
			//return label.getNote().add(noteInfo);

		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Notes not added to Label due to Internel server problem");
		}

	}
	
	@Transactional
	@Override
	public Label updateLabel(String token, long lId, UpdateLabel LabelDto) {

		long userId = (long) generate.parseJWT(token);
		// List<Label> list = new ArrayList<Label>();
		Set<Label> list = labelRepository.findLableByUserId(userId);
		if (list.isEmpty())
			return null;

		/*
		 * java 8 streams feature
		 */
		Label labelData;
		try {

			labelData = list.stream().filter(t -> t.getLId() == lId).findFirst()
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));

			labelData.setLableName(LabelDto.getlName());
			labelData.setUpdateDateAndTime(LocalDateTime.now());
			labelRepository.save(labelData);

		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Label Not Updated due to Internel server problem");
		}

		return labelData;
	}

	@Transactional
	@Override
	public Label removeLabel(String token, long lId) {

		Set<Label> list = this.getLableByUserId(token);
		/*
		 * java 8 streams feature
		 */
		Label data = null;
		try {
			if (list != null) {
				data = list.stream().filter(t -> t.getLId() == lId).findFirst()
						.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));

				labelRepository.delete(data);

				return data;
			}
		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Label Not Deleted due to Internel server problem");
		}
		return null;
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
	public Set<Label> getLableByUserId(String token) {
		// List<Label> note = new ArrayList<>();
		long id = (long) generate.parseJWT(token);
		// List<Label> label=new ArrayList<Label>();
		Set<Label> label = labelRepository.findLableByUserId(id);
		if (label != null) {
			// System.out.println(label + "userlbbb");
			return label;

		}
		return null;
	}

	@Transactional
	@Override
	public Label getLableById(long id) {
		/*
		 * using query
		 */
		Label label = labelRepository.findLableById(id);
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

	

}

//public boolean addExistingNotesToLabel(String labelTitle, String token, long labelId) {
//	long userId = (long) generate.parseJWT(token);
//
//	List<Noteinfo> notes = noteRepository.findNoteByUserId(userId);
//
//	Label label = labelRepository.findLableById(labelId);
//	Noteinfo noteInfo=null;
//	
//	LableDto labelDto=new LableDto();
//	labelDto.setLName(labelTitle);
//	if(label==null) {
//		this.createLable(labelDto, token);
//		this.addNotesToLabel(labelDto, token, label.getLId());
//		
//	}else {
//	
//		 noteInfo = notes.stream().filter(t -> t.getTitle() == noteTitle).findFirst()
//				.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));
//	}
//	try {
//		
//		return noteInfo.getLabel().add(label);
//		//return label.getNote().add(noteInfo);
//
//	} catch (Exception ae) {
//		throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
//				"Notes not added to Label due to Internel server problem");
//	}
//}
