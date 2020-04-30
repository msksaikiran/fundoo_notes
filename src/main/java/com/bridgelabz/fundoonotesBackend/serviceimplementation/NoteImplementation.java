package com.bridgelabz.fundoonotesBackend.serviceimplementation;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.fundoonotesBackend.dto.NoteDto;
import com.bridgelabz.fundoonotesBackend.dto.ReminderDto;
import com.bridgelabz.fundoonotesBackend.dto.TrashNotes;
import com.bridgelabz.fundoonotesBackend.dto.UpdateNote;
import com.bridgelabz.fundoonotesBackend.entity.Label;
import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.entity.Profile;
import com.bridgelabz.fundoonotesBackend.entity.User;
import com.bridgelabz.fundoonotesBackend.exception.NoteException;
import com.bridgelabz.fundoonotesBackend.exception.UserException;
import com.bridgelabz.fundoonotesBackend.repository.NoteRepository;
import com.bridgelabz.fundoonotesBackend.repository.UserRepository;
import com.bridgelabz.fundoonotesBackend.service.IServiceElasticSearch;
import com.bridgelabz.fundoonotesBackend.service.NoteService;
import com.bridgelabz.fundoonotesBackend.utility.JwtGenerator;
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
	private IServiceElasticSearch iServiceElasticSearch;

	@Transactional
	@Override
	public Noteinfo addNotes(NoteDto notes, String token) {
		Noteinfo note = new Noteinfo();
        
		Long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		
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
				//iServiceElasticSearch.createNote(notess);
			} catch (Exception e) {
				throw new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("111"));
			}

			return notess;

	}

	@Transactional
	@Override
	public List<Noteinfo> updateNotes(String token, long nId, UpdateNote updateDto) {

		List<Noteinfo> notes = this.getNoteByUserId(token);

		if (notes.isEmpty())
			return null;
		/*
		 * java 8 streams feature for getting the specified note to the user
		 */
		Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		
			data.setTitle(updateDto.getTitle());
			data.setDescription(updateDto.getDescription());
			data.setUpDateAndTime(LocalDateTime.now());
			Noteinfo notess = noteRepository.save(data);
		
		try {
			//iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("111"));
		}
		
		return notes;
	}

	@Transactional
	@Override
	public Noteinfo removeNotes(String token, long nId) {

		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		/*
		 * java 8 streams feature
		 */
		if (notes.isEmpty())
			return null;

		Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		
		    data.setIsTrashed(1);
		    data.setIsArchieved(0);
			noteRepository.save(data);
		try {
			//iServiceElasticSearch.deleteNote(String.valueOf(nId));
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("111"));
		}
		return notes.get(0);
	}
	@Transactional
	@Override
	public Noteinfo restoreNotes(String token, long nId) {

		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		
		/*
		 * java 8 streams feature
		 */
		if (notes.isEmpty())
			return null;

		Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST,env.getProperty("204")));
		
		    data.setIsTrashed(0);
		    //data.setIsArchieved(0);
			noteRepository.save(data);
		try {
			//iServiceElasticSearch.deleteNote(String.valueOf(nId));
		} catch (Exception ae) {
			//ae.printStackTrace();
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("111"));
		}
		return notes.get(0);
	}
	@Transactional
	@Override
	public Noteinfo deleteNotes(String token, long nId) {

		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		/*
		 * java 8 streams feature
		 */
		if (notes.isEmpty())
			return null;

		Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		
		    data.setIsTrashed(0);
		    //data.setIsArchieved(0);
			noteRepository.delete(data);
		try {
			iServiceElasticSearch.deleteNote(String.valueOf(nId));
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("111"));
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
		//ArrayList<String> noteTitles = new ArrayList<>();
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
		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		if (notes.isEmpty())
			return null;
		Noteinfo data;

		data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		
			data.setIsPinned(0);
			data.setIsArchieved(1);
			data.setUpDateAndTime(LocalDateTime.now());
			Noteinfo notess = noteRepository.save(data);
		try {
			//iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("111"));
		}

		return data;
	}
	
	@Transactional
	@Override
	public Noteinfo unarchieveNote(long nId, String token) {
		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		if (notes.isEmpty())
			return null;
		Noteinfo data;

		data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		
			//data.setIsPinned(0);
			data.setIsArchieved(0);
			data.setUpDateAndTime(LocalDateTime.now());
			Noteinfo notess = noteRepository.save(data);
		try {
			//iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("111"));
		}

		return data;
	}

	@Transactional
	@Override
	public Noteinfo pinNote(long nId, String token) {
		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		Noteinfo notess = null ;
		
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
				data.setIsPinned(1);
				data.setIsArchieved(0);
				data.setUpDateAndTime(LocalDateTime.now());
				notess = noteRepository.save(data);
				return notess;
			}
			
		try {
			//iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			ae.printStackTrace();
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("205"));
		}
		return null;

	}

	@Transactional
	@Override
	public Noteinfo unpinNote(long nId, String token) {
		
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
				data.setIsPinned(0);
				data.setIsArchieved(0);
				data.setUpDateAndTime(LocalDateTime.now());
				
                Noteinfo notess = noteRepository.save(data);
				
				
				return notess;
			}
		try {
				//iServiceElasticSearch.upDateNote(notess);
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
		
			
			return noteRepository.restoreNote(userid);

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

		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		
		/*
		 * java 8 streams feature
		 */
		long nId = Integer.parseInt(id);
		
			if (notes != null) {
				Noteinfo data = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("204")));

				data.setColour(colour);
                Noteinfo notess = noteRepository.save(data);	

			}
		try {
			//iServiceElasticSearch.upDateNote(notess);
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
		//List<Noteinfo> notes = this.getNoteByUserId(token);
		long uId = (long) generate.parseJWT(token);

		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		
		/*
		 * java 8 streams feature
		 */
		
		
			if (notes != null) {
				Noteinfo noteData = notes.stream().filter(t -> t.getNid() == nId).findFirst()
						.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("104")));
				
				noteData.setReminder(reminder.getRemainder());
				//noteRepository.save(noteData);
                Noteinfo notess = noteRepository.save(noteData);
				
				
				
				
			}
		try {
				//iServiceElasticSearch.upDateNote(notess);
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Note Record Not added Reminder due to Internal Error");
		}
		return token;

	}

	@Transactional
	@Override
	public String removeReminder(TrashNotes noteid, String token) {

		long uId = (long) generate.parseJWT(token);
		List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
		/*
		 * java 8 streams feature
		 */
		//int nId = Integer.parseInt(id);
		
			if (notes != null) {
				Noteinfo noteData = notes.stream().filter(t -> t.getNid() == noteid.getNid()).findFirst()
						.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("204")));

				noteData.setReminder(null);
                Noteinfo notess = noteRepository.save(noteData);
				
				
			}
		try {
			//iServiceElasticSearch.upDateNote(notess);

		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR, "Cant able to Remove Reminder");
		}
		return token;

	}

	@Transactional
	@Override
	public List<Noteinfo> getNoteByUserId(String token) {
		
		    long uId = (long) generate.parseJWT(token);

			List<Noteinfo> notes = noteRepository.findNoteByUserId(uId);
            List<Long> clnotes = noteRepository.getCollabrateNotes(uId);
            /**
             * merging of collabrated notes and actuall User Notes
             */
            Iterable<Noteinfo> clNoteInfo = noteRepository.findAllById(clnotes);
            notes.addAll((Collection<? extends Noteinfo>) clNoteInfo);
            
			if (notes != null) {
				return notes;
			} else {
				new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("104"));
			}
		return null;
	}

	public List<Long> getCollabrateNotes(String token) {
		long uId = (long) generate.parseJWT(token);
		 List<Long> collabareNotes = noteRepository.getCollabrateNotes(uId);
		 return collabareNotes;
	}
	
	@Transactional
	@Override
	public List<String> getNote(String id) {

		Noteinfo notes = noteRepository.findNoteById(Integer.parseInt(id))
				.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("204")));
		List<String> labelnames=new ArrayList<>();
		List<Label> labeldetails = notes.getLabel();
		for(Label labelinfo:labeldetails) {
			labelnames.add(labelinfo.getLableName());
		}
		return labelnames;

	}
	@Transactional
	@Override
	public Noteinfo getNotedetails(String id) {

		Noteinfo notes = noteRepository.findNoteById(Integer.parseInt(id))
				.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("204")));

		return notes;

	}

	private String awsS3AudioBucket;

	private AmazonS3 amazonS3;
	
	@Override
	public ArrayList<String> getImageUrl(long id) {
		//long id = (Long) generate.parseJWT(token);
		ArrayList<String> imageurls=new ArrayList<>();
		Noteinfo notes = noteRepository.findNoteById(id)
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("104")));
		List<Profile> data =notes.getProfile();
		for(Profile url:data) {
			imageurls.add("https://msksaikiran.s3.us-east-2.amazonaws.com/"+url.getIName());
		}
		return imageurls;
	}
	
	@Autowired
    public void AmazonS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket) 
    { 
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }

	@Async
	public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, long id) {
		//long id = (Long) generate.parseJWT(token);

		Profile image=new Profile();
		
		Noteinfo notes = noteRepository.findNoteById(id)
				.orElseThrow(() -> new NoteException(HttpStatus.BAD_REQUEST, env.getProperty("104")));
		String fileName = multipartFile.getOriginalFilename();

		image.setIName(fileName);
		
        notes.getProfile().add(image);
		
		try {
			// creating the file in the server (temporarily)
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();

			PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);
 
			if (enablePublicReadAccess) {
				putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
			}
			this.amazonS3.putObject(putObjectRequest);
			// removing the file created in the server
			file.delete();
			noteRepository.save(notes);
		} catch (IOException | AmazonServiceException ex) {
			ex.printStackTrace();
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
	}

	@Async
	public void deleteFileFromS3Bucket(String fileName, String token) {
		long id = (Long) generate.parseJWT(token);

		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		user.setProfile("null");
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
		} catch (AmazonServiceException ex) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
		userRepository.save(user);
	}

}