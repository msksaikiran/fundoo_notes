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
import org.springframework.beans.BeanUtils;
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
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user not exist"));;
		if (user != null) {
			Label label = (Label) modelMapper.map(labelDto, Label.class);
			//label.set;
			return labelRepository.save(label);
		}
		// BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}

//	@Transactional
//	@Override
//	public void createLabelAndMAp(LabelDto labelDto, String token) {
//		Long id = null;
//		try {
//			id = (Long) generate.parseJWT(token);
//		} catch (Exception e) {
//			throw new UserException("user doesn't exist with id");
//		}
//		UserInformation user = Repository.findUserById(id);
//		if (user == null) {
//			BeanUtils.copyProperties(labelDto, LabelDto.class);
//
//			labelData.setUserId(user.getUserId());
//			LabelRepo.save(labelData);
//			NoteData note = noteRepo.findById(id);
//
//			note.getList().add(labelData);
//			noteRepo.save(note);
//		} else {
//			throw new UserException("label with that name is already present ");
//		}
//	}
	
	@Transactional
	@Override
	public Label addNotesToLabel(NoteDto notes, String token,String labelId) {
		
	//	List<Label> labek=new ArrayL
		Noteinfo note = noteService.addNotes(notes, token);
		
		int lId = Integer.parseInt(labelId);
		
		List<Label> lables = this.getLableByUserId(token);
		
		try {
	    Optional<Label> labelInfo = lables.stream().filter(t->t.getLId()==lId).findFirst();

		labelInfo.ifPresent(data->{

//			data.setLabelId(lId);
//			data.setUserId(1);
//			data.setUpdateDateAndTime(LocalDateTime.now());
//			data.setLableName("dsad");
			//data.
			System.out.println("###"+data);
			//note.getLable().add(data);
			//note.setLable(data.getUserId());
		});
		
		if(labelInfo.equals(Optional.empty())) {
			return null;
		}
		
		}catch(Exception ae) {
			ae.printStackTrace();
		}
		return lables.get(0);
		
	}
		
	@Transactional
	@Override
	public Label updateLabel(String id, String token, UpdateLabel LabelDto) {

		int lId = Integer.parseInt(id);
		List<Label> list = this.getLableByUserId(token);
		/*
		 * java 8 streams feature
		 */
		try {
			if (list != null) {
				Optional<Label> data = list.stream().filter(t -> t.getLId() == lId).findFirst();
				data.ifPresent(da -> {
					da.setLableName(LabelDto.getlName());
					da.setUpdateDateAndTime(LocalDateTime.now());
					labelRepository.save(da);
				});
				// System.out.println("data::"+data);
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,"Label Not Updated due to Internel server problem");
		}

		return list.get(0);
	}

	@Transactional
	@Override
	public List<Label> removeLabel(String token, String id) {

		int lId = Integer.parseInt(id);
		List<Label> list = this.getLableByUserId(token);
		/*
		 * java 8 streams feature
		 */
		Optional<Label> data = null;
		try {
			if (list != null) {
				data = list.stream().filter(t -> t.getLId() == lId).findFirst();
				data.ifPresent(da -> {
					labelRepository.delete(da);
				});
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,"Label Not Deleted due to Internel server problem");
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
	public List<Label> getLableByUserId(String token) {
		// List<Label> note = new ArrayList<>();
		long id = (long) generate.parseJWT(token);
		List<Label> label = labelRepository.findLableByUserId(id);
		if (label != null) {
			System.out.println(label+"userlbbb");
			return label;
			
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
