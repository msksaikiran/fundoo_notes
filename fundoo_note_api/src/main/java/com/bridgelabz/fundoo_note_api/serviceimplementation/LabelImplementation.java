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
import java.util.Optional;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo_note_api.dto.LableDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateLabel;
import com.bridgelabz.fundoo_note_api.entity.Label;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.NotesNotFoundException;
import com.bridgelabz.fundoo_note_api.repository.LabelRepository;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.LabelService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@Service
public class LabelImplementation implements LabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JwtGenerator generate;

	@Transactional
	@Override
	public Label createLable(LableDto labelDto, String token) {
		int userId = (Integer) generate.parseJWT(token);
		System.out.println(userId);
		User user = userRepository.getUserById(userId);
		if (user == null) {
			Label label = (Label) modelMapper.map(labelDto, Label.class);
			label.setUserId(userId);
			return labelRepository.save(label);
		}
		// BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}

	@Transactional
	@Override
	public Label updateLabel(String id, UpdateLabel LabelDto) {

		List<Label> list = this.getAllLables();
		/*
		 * java 8 streams feature
		 */
		int lId = Integer.parseInt(id);
		try {
			Optional<Label> data = list.stream().filter(t -> t.getLabelId() == lId).findFirst();
			data.ifPresent(da -> {
				da.setLableName(LabelDto.getlName());
				da.setUpdateDateAndTime(LocalDateTime.now());
				labelRepository.save(da);
			});
			// System.out.println("data::"+data);
			if (data.equals(Optional.empty())) {
				return null;
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("Label Record Not Exist");
		}

		return list.get(0);
	}

	@Transactional
	@Override
	public List<Label> removeLabel(String id) {

		int lId = Integer.parseInt(id);
		List<Label> list = this.getAllLables();
		System.out.println(list);
		/*
		 * java 8 streams feature
		 */
		Optional<Label> data = null;
		try {
			data = list.stream().filter(t -> t.getLabelId() == lId).findFirst();
			data.ifPresent(da -> {
				labelRepository.delete(da);
			});
			if (data.equals(Optional.empty())) {
				return null;
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("user Record Not Exist");
		}
		/*
		 * java 5 Extendedfor feature
		 */
		// for (Label ls : list) {
		// if (ls.getLabelId() == (Integer.parseInt(id))) {
		//
		// return ls;
		// }
		// }
		return list;
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
		// System.out.println("NotesId:"+lis);
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
	public List<Label> getLableByUserId(String id) {
		List<Label> note = new ArrayList<>();
		labelRepository.findLableByUserId(Integer.parseInt(id)).forEach(note::add);
		if (note != null) {

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
		if (label != null) {
			return label;
		}
		return null;

	}
}
