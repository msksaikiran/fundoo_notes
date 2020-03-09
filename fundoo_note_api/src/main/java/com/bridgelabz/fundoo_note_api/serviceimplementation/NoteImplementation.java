package com.bridgelabz.fundoo_note_api.serviceimplementation;

/*#
 * Description: implementation part for Note when user makes the request for add_notes,update,read,delete notes
 * @author : SaiKiranMsk
 *     
 */
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.ReminderDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.NoteException;
import com.bridgelabz.fundoo_note_api.exception.UserException;
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
	private JwtGenerator generate;

	@Transactional
	@Override
	public Noteinfo addNotes(NoteDto notes, String token) {
		Noteinfo note=new Noteinfo();
		try {
			Long id = (Long) generate.parseJWT(token);
			User user = userRepository.getUserById(id)
					.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user not exist"));
		

				BeanUtils.copyProperties(notes, note);
				note.setColour("ash");
				note.setIsArchieved(0);
				note.setCreatedDateAndTime(LocalDateTime.now());
				note.setIsPinned(0);
				note.setIsTrashed(0);
				note.setReminder(null);
				note.setTitle(notes.getTitle());
				note.setDescription(notes.getDescription());
				user.getNote().add(note);
				
				return noteRepository.save(note);
	
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Adding the Notes Failed due to Incorrect Fields");
		}
	}

	@Transactional
	@Override
	public List<Noteinfo> updateNotes(String token, String id, UpdateNote updateDto) {

		List<Noteinfo> notes = this.getNoteByUserId(token);

		if(notes.isEmpty())
			return null;
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));
				//data.ifPresent(da -> {
					data.setTitle(updateDto.getTitle());
					data.setUpDateAndTime(LocalDateTime.now());
					noteRepository.save(data);
				//});
//				if (data.equals(Optional.empty())) {
//					return null;
//				}
			
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Updated due to Internal Error");
		}
		return notes;
	}

	@Transactional
	@Override
	public Noteinfo removeNotes(String token, String id) {

		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		if(notes.isEmpty())
			return null;
		
		int nId = Integer.parseInt(id);
		try {
			
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));;
				//data.ifPresent(da -> {
					noteRepository.delete(data);
				//});
//				if (data.equals(Optional.empty())) {
//					return null;
//				}
			
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Removed due to Internal Error");
		}
		return notes.get(0);
	}

	@Transactional
	@Override
	public List<Noteinfo> getAllNotes() {
		List<Noteinfo> notes = new ArrayList<>();
		noteRepository.findAll().forEach(notes::add);
		return notes;
	}

	@Override
	public List<String> ascSortByName() {
		ArrayList<String> noteTitles = new ArrayList<>();
		List<Noteinfo> Notelist = this.getAllNotes();
		/*
		 * java 8 lambda feature for sorting
		 */
		Notelist.forEach(t -> {
			noteTitles.add(t.getTitle());
		});
		Collections.sort(noteTitles);
		return noteTitles;
	}

	@Transactional
	@Override
	public ArrayList<String> sortByName() {
		ArrayList<String> noteTitles = new ArrayList<>();
		List<Noteinfo> Notelist = this.getAllNotes();
		/*
		 * java 8 lambda feature for sorting
		 */
		Notelist.forEach(t -> {
			noteTitles.add(t.getTitle());
		});
		Collections.sort(noteTitles, Collections.reverseOrder());
		return noteTitles;
	}

	@Transactional
	@Override
	public String archieveNote(String id, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		
		if(notes.isEmpty())
			return null;
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			//if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));
				//data.ifPresent(da -> {
					data.setIsPinned(0);
					data.setIsArchieved(1);
					data.setUpDateAndTime(LocalDateTime.now());
					noteRepository.save(data);
				//});
				//if (data.equals(Optional.empty())) {
				//	return "notes not archieved";
				//}
			//}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not archieved due to Internal Error");
		}
		return token;
	}

	@Transactional
	@Override
	public Noteinfo pinNote(String id, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t ->t.getNid() == nId).findFirst()
				               .orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));
				//data.ifPresent(da -> {
					data.setIsPinned(1);
					data.setIsArchieved(0);
					data.setUpDateAndTime(LocalDateTime.now());
					return noteRepository.save(data);
				//});
				//if (data.equals(Optional.empty())) {
				//	return "notes not pinned";
				//}
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Pinned due to Internal Error");
		}
		return null;

	}

	@Transactional
	@Override
	public List<Noteinfo> getAlltrashednotes(String token) {

		try {
			long userid = (Long) generate.parseJWT(token);
			User userData = userRepository.getUserById(userid)
					.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user not exist"));;
			//if (userData != null) {
				return  noteRepository.restoreNote(userid);
			//}
		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Trashed due to Internal Error");
		}
	}

	@Transactional
	@Override
	public List<Noteinfo> getarchieved(String token) {
		try {
			long userid = (long) generate.parseJWT(token);
			User userData = userRepository.getUserById(userid)
					.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, "user not exist"));;
			//if (userData != null) {
				return  noteRepository.getArchievedNotes(userid);
				
			//}
		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Not able to get ArchiveNotes  due to Internal Error");
		}

	}

	@Transactional
	@Override
	public String addColour(String id, String token, String colour) {

		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		long nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(()->new NoteException(HttpStatus.BAD_REQUEST,"Note not exist on the user"));
			//	data.ifPresent(da -> {
					data.setColour(colour);
					noteRepository.save(data);
				//});
				//if (data.equals(Optional.empty())) {
					//return "notes not coloured";
				//}
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Coloured due to Internal Error");
		}
		return colour;
	}

	@Transactional
	@Override
	public List<Noteinfo> getAllPinneded(String token) {
		try {
			long userid = (long) generate.parseJWT(token);
			User userData = userRepository.getUserById(userid)
					.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, "user not exist"));
			//if (userData != null) {
				List<Noteinfo> list = noteRepository.getPinnededNotes(userid);
				return list;
			//}
		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not Pinned due to Internal Error");
		}
		
	}

	@Transactional
	@Override
	public String addReminder(String id, String token, ReminderDto reminder) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Noteinfo noteData = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, "user not exist"));
				//data.ifPresent(noteData -> {
					noteData.setReminder(reminder.getRemainder());
					noteRepository.save(noteData);
				//});
				if (noteData.equals(Optional.empty())) {
					return "notes not pinned";
				}
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not added Reminder due to Internal Error");
		}
		return token;

	}

	@Transactional
	@Override
	public String removeReminder(String id, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Noteinfo noteData = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, "Notes not exist to the specified User"));
				//data.ifPresent(noteData -> {
					noteData.setReminder(null);
					noteRepository.save(noteData);
				//});
				if (noteData.equals(Optional.empty())) {
					return "notes not pinned";
				}
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Cant able to Remove Reminder");
		}
		return token;

	}

	@Transactional
	@Override
	public List<Noteinfo> getNoteByUserId(String token) {

		long uId = (long) generate.parseJWT(token);

		try {
			List<Noteinfo> user = noteRepository.findNoteByUserId(uId);

			if (user != null) {
				return user;
			}else {
				new NoteException(HttpStatus.BAD_REQUEST,"user not Exist");
			}
		} catch (Exception ae) {
			
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Cant able to get the user");
		}
		return null;
	}

	@Transactional
	@Override
	public Noteinfo getNote(String id) {
		
		Noteinfo notes = noteRepository.findNoteById(Integer.parseInt(id))
				.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, "Notes does not exist "));
		
			return notes;
		
	}

}