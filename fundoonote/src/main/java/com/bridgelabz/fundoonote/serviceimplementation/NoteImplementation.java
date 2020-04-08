package com.bridgelabz.fundoonote.serviceimplementation;

/*#
 * Description: implementation part for Note when user makes the request for add_notes,update,read,delete notes
 * @author : SaiKiranMsk
 *     
 */
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.dto.ReminderDto;
import com.bridgelabz.fundoonote.dto.TrashNotes;
import com.bridgelabz.fundoonote.dto.UpdateNote;
import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.entity.User;
import com.bridgelabz.fundoonote.exception.NoteException;
import com.bridgelabz.fundoonote.exception.UserException;
import com.bridgelabz.fundoonote.repository.NoteRepository;
//import com.bridgelabz.fundoonote.repository.SearchResult;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.IServiceElasticSearch;
//import com.bridgelabz.fundoonote.service.IServiceElasticSearch;
import com.bridgelabz.fundoonote.service.NoteService;
import com.bridgelabz.fundoonote.utility.JwtGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:message.properties")
public class NoteImplementation implements NoteService {
	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtGenerator generate;

	@Autowired
	private Environment env;

	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private IServiceElasticSearch iServiceElasticSearch;

	@Transactional
	@Override
	public Noteinfo addNotes(NoteDto notes, String token) {
		Noteinfo note = new Noteinfo();
        
		Long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		try {
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
			Noteinfo notess = noteRepository.save(note);
			
			try {
				iServiceElasticSearch.createNote(notess);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return notess;

		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("104"));
		}
	}

	@Transactional
	@Override
	public List<Noteinfo> updateNotes(String token, long nId, UpdateNote updateDto) {

		List<Noteinfo> notes = this.getNoteByUserId(token);

		if (notes.isEmpty())
			return null;
		/*
		 * java 8 streams feature
		 */
		
		Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		try {
			data.setTitle(updateDto.getTitle());
			data.setUpDateAndTime(LocalDateTime.now());
			Noteinfo notess = noteRepository.save(data);

			iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("209"));
		}
		
		return notes;
	}

	@Transactional
	@Override
	public Noteinfo removeNotes(String token, long nId) {

		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		if (notes.isEmpty())
			return null;

		Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		try {
		    data.setIsTrashed(1);
		    data.setIsArchieved(0);
			noteRepository.save(data);

			iServiceElasticSearch.deleteNote(String.valueOf(nId));
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("210"));
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
	public List<Noteinfo> sortByName() {
		ArrayList<String> noteTitles = new ArrayList<>();
		List<Noteinfo> Notelist = this.getAllNotes();
		/*
		 * java8 parallel sort
		 */
		List<Noteinfo> notelist = Notelist.parallelStream().sorted(Comparator.comparing(Noteinfo::getTitle))
				.collect(Collectors.toList());
		return notelist;

		/*
		 * java 8 lambda feature for sorting
		 */
		
		// Notelist.forEach(t -> {
		// noteTitles.add(t.getTitle());
		// });
		// Collections.sort(noteTitles, Collections.reverseOrder());

		// return noteTitles;
	}

	@Transactional
	@Override
	public Noteinfo archieveNote(long nId, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);

		if (notes.isEmpty())
			return null;
		Noteinfo data;

		data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		try {
			data.setIsPinned(0);
			data.setIsArchieved(1);
			data.setUpDateAndTime(LocalDateTime.now());
			Noteinfo notess = noteRepository.save(data);

			iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("206"));
		}

		return data;
	}

	@Transactional
	@Override
	public Noteinfo pinNote(long nId, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		try {
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
				data.setIsPinned(1);
				data.setIsArchieved(0);
				data.setUpDateAndTime(LocalDateTime.now());
				Noteinfo notess = noteRepository.save(data);
				
				iServiceElasticSearch.upDateNote(notess);
				return notess;
			}
		} catch (Exception ae) {
			ae.printStackTrace();
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("205"));
		}
		return null;

	}

	@Transactional
	@Override
	public Noteinfo unpinNote(long nId, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		
		try {
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
				data.setIsPinned(0);
				data.setIsArchieved(0);
				data.setUpDateAndTime(LocalDateTime.now());
				
                Noteinfo notess = noteRepository.save(data);
				
				iServiceElasticSearch.upDateNote(notess);
				return notess;
			}
		} catch (Exception ae) {
			ae.printStackTrace();
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("205"));
		}
		return null;

	}
	
	@Transactional
	@Override
	public List<Noteinfo> getAlltrashednotes(String token) {

		long userid = (Long) generate.parseJWT(token);
		   userRepository.getUserById(userid)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		try {
			
			return noteRepository.restoreNote(userid);

		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, "Note Record Not Trashed due to Internal Error");
		}
	}

	@Transactional
	@Override
	public List<Noteinfo> getarchieved(String token) {

		long userid = (long) generate.parseJWT(token);
		  userRepository.getUserById(userid)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		try {
			return noteRepository.getArchievedNotes(userid);

		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Not able to get ArchiveNotes  due to Internal Error");
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
						.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));

				data.setColour(colour);
                Noteinfo notess = noteRepository.save(data);
				
				iServiceElasticSearch.upDateNote(notess);
				

			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, "Note Record Not Coloured due to Internal Error");
		}
		return colour;
	}

	@Transactional
	@Override
	public List<Noteinfo> getAllPinneded(String token) {

		long userid = (long) generate.parseJWT(token);
		    userRepository.getUserById(userid)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("104")));
		try {
			List<Noteinfo> list = noteRepository.getPinnededNotes(userid);
			return list;

		} catch (Exception e) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, "Note Record Not Pinned due to Internal Error");
		}

	}

	@Transactional
	@Override
	public String addReminder(long nId, String token, ReminderDto reminder) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		
		try {
			if (notes != null) {
				Noteinfo noteData = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("104")));
				
				noteData.setReminder(reminder.getRemainder());
				//noteRepository.save(noteData);
                Noteinfo notess = noteRepository.save(noteData);
				
				iServiceElasticSearch.upDateNote(notess);
				
				
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Note Record Not added Reminder due to Internal Error");
		}
		return token;

	}

	@Transactional
	@Override
	public String removeReminder(TrashNotes noteid, String token) {
		List<Noteinfo> notes = this.getNoteByUserId(token);
		/*
		 * java 8 streams feature
		 */
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Noteinfo noteData = notes.stream().filter(t -> t.getNid() == noteid.getNid()).findFirst()
						.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("204")));

				noteData.setReminder(null);
                Noteinfo notess = noteRepository.save(noteData);
				
				iServiceElasticSearch.upDateNote(notess);

			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, "Cant able to Remove Reminder");
		}
		return token;

	}

	@Transactional
	@Override
	@Cacheable(value = "twenty-second-cache", key = "'tokenInCache'+#token", condition = "#isCacheable != null && #isCacheable")
	public List<Noteinfo> getNoteByUserId(String token) {

		long uId = (long) generate.parseJWT(token);

		try {
			List<Noteinfo> user = noteRepository.findNoteByUserId(uId);

			if (user != null) {
				return user;
			} else {
				new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("104"));
			}
		} catch (Exception ae) {

			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, "Cant able to get the user");
		}
		return null;
	}

	@Transactional
	@Override
	public Noteinfo getNote(String id) {

		Noteinfo notes = noteRepository.findNoteById(Integer.parseInt(id))
				.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("204")));

		return notes;

	}

}