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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo_note_api.dto.NoteDto;
import com.bridgelabz.fundoo_note_api.dto.ReminderDto;
import com.bridgelabz.fundoo_note_api.dto.UpdateNote;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.NotesNotFoundException;
import com.bridgelabz.fundoo_note_api.exception.UserNotFoundException;
import com.bridgelabz.fundoo_note_api.repository.LabelRepository;
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
	private ModelMapper modelMapper;

	@Autowired
	private JwtGenerator generate;

	
	
	@Transactional
	@Override
	public Noteinfo addNotes(NoteDto notes, String token) {

		try {
			Integer id = (Integer) generate.parseJWT(token);
			User user = userRepository.getUserById(id);
			if (user != null) {
				Noteinfo note = (Noteinfo) modelMapper.map(notes, Noteinfo.class);

				note.setColour("ash");
				note.setIsArchieved(0);
				note.setCreatedDateAndTime(LocalDateTime.now());
				note.setIsPinned(0);
				note.setIsTrashed(0);
				note.setReminder(null);
				note.setTitle(notes.getTitle());
				note.setDescription(notes.getDescription());
				note.setUser(user);
				return noteRepository.save(note);
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("user Not registered");
		}
		// BeanUtils.copyProperties(notes,Noteinfo.class);
		return null;
	}


	@Transactional
	@Override
	public List<Noteinfo> updateNotes(String token, String id, UpdateNote updateDto) {

		// List<Noteinfo> notes = this.getNoteByUserId(token);
		List<Noteinfo> notes = this.getNoteByUserId(token);

		/*
		 * java 8 streams feature
		 */
		int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<Noteinfo> data = notes.stream().filter(t -> t.getNoteId() == nId).findFirst();
				data.ifPresent(da -> {
					da.setTitle(updateDto.getTitle());
					da.setUpDateAndTime(LocalDateTime.now());
					noteRepository.save(da);
				});
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new NotesNotFoundException("Label Record Not Exist");
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
		int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<Noteinfo> data = notes.stream().filter(t -> t.getNoteId() == nId).findFirst();
				data.ifPresent(da -> {
					noteRepository.delete(da);
				});
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new UserNotFoundException("user Not registered");
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
	public void archieveNote(String id, String token) {
		try {
	int userid=(int)generate.parseJWT(token);
	User userData = userRepository.getUserById(userid);
	Noteinfo noteData = noteRepository.findNoteById(Integer.parseInt(id));
	if(noteData!=null) {
		noteData.setIsPinned(0);
		noteData.setIsArchieved(1);
		noteData.setUpDateAndTime(LocalDateTime.now());
		noteRepository.save(noteData);
	}
		}
		catch(Exception e) {
			throw new UserNotFoundException("user is not present");
	}
	}


//@Transactional
//	@Override
//	public void pinNote(String id, String token) {
//	try {
//		int userid=(int)generate.parseJWT(token);
//		User userData = userRepository.getUserById(userid);
//		 Noteinfo noteData = noteRepository.findNoteById(Integer.parseInt(id));
//		if(noteData!=null) {
//			noteData.setIsArchieved(0);
//			noteData.setIsPinned(1);
//			noteData.setUpDateAndTime(LocalDateTime.now());
//			noteRepository.save(noteData);
//	}
//	}catch(Exception e) {
//		throw new UserNotFoundException("user is not present");
//}	
//	}

//@Transactional
//@Override
//public List<Noteinfo> gettrashednotes(String token) {
//	try {
//		int userid=(int)generate.parseJWT(token);
//		User userData = userRepository.getUserById(userid);
//		if(userData!=null) {
//			List<Noteinfo> list =noteRepository.restoreNote(userid);
//			return list;
//		}
//		}		catch (Exception e) {
//				throw new UserNotFoundException("error occured ");
//		}
//	return null;
//	}
//@Transactional
//@Override
//public List<Noteinfo> getarchieved(String token) {
//		try {
//			int userid=(int)generate.parseJWT(token);
//			User userData = userRepository.getUserById(userid);
//			if(userData!=null) {
//				List<Noteinfo> list =noteRepository.getArchievedNotes(userid);
//				return list;
//			}
//			}		catch (Exception e) {
//					throw new UserNotFoundException("error occured ");
//			}
//		return null;
//
//}
//@Transactional
//@Override
//public void addColour(String noteId, String token, String colour) {
//	try {
//		int userid=(int)generate.parseJWT(token);
//		User userData = userRepository.getUserById(userid);
//	    Noteinfo noteData = noteRepository.findNoteById(Integer.parseInt(noteId));
//		if(noteData!=null) {
//			noteData.setColour(colour);
//			noteRepository.save(noteData);
//			}}catch(Exception e) {
//			throw new UserNotFoundException("user is not present");
//	}	
//}
//@Transactional
//@Override
//public List<Noteinfo> getPinneded(String token) {
//	try {
//		int userid=(int)generate.parseJWT(token);
//		User userData = userRepository.getUserById(userid);
//		if(userData!=null) {
//			List<Noteinfo> list =noteRepository.getPinnededNotes(userid);
//			return list;
//		}
//		}		catch (Exception e) {
//				throw new UserNotFoundException("error occured ");
//		}
//}
//@Transactional
//@Override
//public void addReminder(String id, String token, ReminderDto reminder) {
//
//	try {
//		int userid=(int)generate.parseJWT(token);
//		User userData = userRepository.getUserById(userid);
//		 Noteinfo noteData = noteRepository.findNoteById(Integer.parseInt(id));
//		if(noteData!=null) {
//			noteData.setReminder(reminder.getRemainder());
//			noteRepository.save(noteData);
//			}}catch(Exception e) {
//			throw new UserNotFoundException("user is not present");
//	}	
//	
//}

//@Transactional
//@Override
//public void removeReminder(Long noteid, String token, ReminderDto remainder) {
//
//	try {
//		Long userid=(Long)generate.parseJWT(token);
//		userData = repository.findUserById(userid);
//		NoteData noteData = noteRepository.findById(noteid);
//		if(noteData!=null) {
//			noteData.setReminder(null);
//			noteRepository.save(noteData);
//			}}catch(Exception e) {
//			throw new UserException("user is not present");
//	}	
//	
//		}
	@Transactional
	@Override
	public List<Noteinfo> getNoteByUserId(String token) {

		int uId = (Integer) generate.parseJWT(token);

		try {
			List<Noteinfo> user = noteRepository.findNoteByUserId(uId);

			if (user != null) {
				return user;
			}
		} catch (Exception ae) {
			ae.printStackTrace();
			throw new NotesNotFoundException("invalid Number registered");
		}
		return null;
	}

	@Transactional
	@Override
	public Noteinfo getNote(String id) {
		/*
		 * using query
		 */
		Noteinfo notes = noteRepository.findNoteById(Integer.parseInt(id));
		if (notes != null) {
			return notes;
		}

		/*
		 * without using the query
		 */

		/*
		 * Login userlogindto = new Login(); List<Noteinfo> list = this.getAllNotes();
		 * 
		 * for (Noteinfo ls : list) { if (ls.getNoteId() == Integer.parseInt(id)) {
		 * userlogindto.setEmail(ls.getTitle());
		 * userlogindto.setPassword(ls.getDescription()); return ls; } }
		 */
		return null;

	}

}