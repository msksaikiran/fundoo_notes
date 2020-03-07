package com.bridgelabz.fundoo_note_api.serviceimplementation;

/*#
 * Description: implementation part for Label when user makes the request for add_label,update,read,delete label
 * @author : SaiKiranMsk
 *     
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo_note_api.dto.LableDto;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateLabel;
import com.bridgelabz.fundoo_note_api.entity.Label;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.LabelException;
import com.bridgelabz.fundoo_note_api.exception.UserException;
import com.bridgelabz.fundoo_note_api.repository.LabelRepository;
import com.bridgelabz.fundoo_note_api.repository.NoteRepository;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.LabelService;
import com.bridgelabz.fundoo_note_api.service.NoteService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@Service
public class LabelImplementation implements LabelService {

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

	@Autowired
	private NoteService noteService;

	@Transactional
	@Override
	public Label createLable(LableDto labelDto, String token) {
		long userId = (long) generate.parseJWT(token);
		System.out.println(userId);
		User user = userRepository.getUserById(userId)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user not exist"));
		

		Label label = (Label) modelMapper.map(labelDto, Label.class);
		label.setUserId(userId);
		return labelRepository.save(label);

	}

	@Transactional
	@Override
	public Label addNotesToLabel(NoteDto notes, String token, long lId) {

		Noteinfo note = noteService.addNotes(notes, token);

		List<Label> lables = this.getLableByUserId(token);

		try {
			Label labelInfo = lables.stream().filter(t -> t.getLId() == lId).findFirst()
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));

			labelInfo.getNote().add(note);

		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Notes not added to Label due to Internel server problem");
		}
		return lables.get(0);

	}

	@Transactional
	@Override
	public boolean addExistingNotesToLabel(long noteId, String token, long labelId) {
		long userId = (long) generate.parseJWT(token);

		List<Noteinfo> notes = noteRepository.findNoteByUserId(userId);

		Label label = labelRepository.findLableById(labelId);

		try {
			Noteinfo noteInfo = notes.stream().filter(t -> t.getNid() == noteId).findFirst()
					.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));

			return label.getNote().add(noteInfo);

		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Notes not added to Label due to Internel server problem");
		}

	}

	@Transactional
	@Override
	public Label updateLabel(String token, long lId, UpdateLabel LabelDto) {

		// int lId = Integer.parseInt(id);
		long userId = (long) generate.parseJWT(token);
		List<Label> list = labelRepository.findLableByUserId(userId);
		if (list.isEmpty())
			return null;

		/*
		 * java 8 streams feature
		 */
		try {

			Label labelData = list.stream().filter(t -> t.getLId() == lId).findFirst().orElseThrow(
					() -> new LabelException(HttpStatus.BAD_REQUEST, "Label id doesn't Exist to the user"));

			labelData.setLableName(LabelDto.getlName());
			labelData.setUpdateDateAndTime(LocalDateTime.now());
			labelRepository.save(labelData);

		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Label Not Updated due to Internel server problem");
		}

		return list.get(0);
	}

	@Transactional
	@Override
	public List<Label> removeLabel(String token, long lId) {

		// int lId = Integer.parseInt(id);
		List<Label> list = this.getLableByUserId(token);
		/*
		 * java 8 streams feature
		 */
		Label data = null;
		try {
			if (list != null) {
				data = list.stream().filter(t -> t.getLId() == lId).findFirst().orElseThrow(
						() -> new LabelException(HttpStatus.BAD_REQUEST, "Label id doesn't Exist to the user"));

				labelRepository.delete(data);

			}
		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Label Not Deleted due to Internel server problem");
		}
		return list;
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
	public List<Label> getLableByUserId(String token) {
		// List<Label> note = new ArrayList<>();
		long id = (long) generate.parseJWT(token);
		List<Label> label = labelRepository.findLableByUserId(id);
		if (label != null) {
			System.out.println(label + "userlbbb");
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

}
