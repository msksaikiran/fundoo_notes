package com.bridgelabz.fundoonotesBackend.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesBackend.dto.NoteDto;
import com.bridgelabz.fundoonotesBackend.dto.PinDto;
import com.bridgelabz.fundoonotesBackend.dto.ReminderDto;
import com.bridgelabz.fundoonotesBackend.dto.TrashNotes;
import com.bridgelabz.fundoonotesBackend.dto.UpdateNote;
import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.response.NoteResponse;
import com.bridgelabz.fundoonotesBackend.service.IServiceElasticSearch;
import com.bridgelabz.fundoonotesBackend.service.LabelService;
import com.bridgelabz.fundoonotesBackend.service.NoteService;

@RestController
@RequestMapping("/notes")
@PropertySource("classpath:message.properties")
public class NotesController {
	@Autowired
	private NoteService noteService;

	@Autowired
	private LabelService labelService;
	
	@Autowired
	private Environment env;
	
	@ Autowired
	IServiceElasticSearch esService;

	
	/**
	 * API to add the Note Details
	 */
	@PostMapping(value = "/users/{token}")
	public ResponseEntity<NoteResponse> createNotes(@RequestBody NoteDto notes, @PathVariable String token) {

		
		Noteinfo note = noteService.addNotes(notes, token);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("201"),note,200));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new NoteResponse(env.getProperty("102"),notes,401));
		}
	}

	/**
	 * API to Update the Note Details By Id
	 */
	@PutMapping(value = "/update/{token}")
	public ResponseEntity<NoteResponse> updateNote(@PathVariable String token,@RequestBody UpdateNote dto) {
		List<Noteinfo> note = noteService.updateNotes(token,dto.getNid(), dto);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("201"),note,200));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new NoteResponse(env.getProperty("204"),note,200));
	}

	/**
	 * API to getting the Note Details By Id
	 */

	@GetMapping(value = "/notelabels/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable String id) {
		List<String> note = noteService.getNote(id);
		if (note != null) {
			// String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("201"),note,200));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new NoteResponse(env.getProperty("204"),note,200));
	}
	
	@GetMapping(value = "/notesdetails/{id}")
	public ResponseEntity<NoteResponse> getNotedetails(@PathVariable String id) {
		Noteinfo note = noteService.getNotedetails(id);
		if (note != null) {
			// String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("201"),note,200));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new NoteResponse(env.getProperty("204"),note,200));
	}
	/**
	 * API to getting the Note Details By User_Id
	 */

	@GetMapping(value = "/users/{token}")
	public List<Noteinfo> getNotesByUserId(@PathVariable String token) {
		List<Noteinfo> note = noteService.getNoteByUserId(token);
	
		return note;
//		if (note != null) {
//			return ResponseEntity.status(HttpStatus.CREATED)
//					.body(new NoteResponse(env.getProperty("211"),note,200));
//		}
//		return ResponseEntity.status(HttpStatus.CREATED)
//				.body(new NoteResponse(env.getProperty("102"),note,200));
	}

	/**
	 * API to deleting the Note Details By _Id
	 */
	@PutMapping(value = "/trash/{token}")
	public ResponseEntity<NoteResponse> deleteNote(@RequestBody TrashNotes notes, @PathVariable String token) {
		Noteinfo note = noteService.removeNotes(token, notes.getNid());
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("203"),note,200));
		
		
	}
	
	@PutMapping(value = "/restore/{token}")
	public ResponseEntity<NoteResponse> restoreNote(@RequestBody TrashNotes notes, @PathVariable String token) {
		Noteinfo note = noteService.restoreNotes(token, notes.getNid());
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("203"),note,200));
		
		
	}
	
	@PostMapping(value = "/permenantDelete/{token}")
	public ResponseEntity<NoteResponse> permenantDeleteNote(@RequestBody TrashNotes notes, @PathVariable String token) {
		Noteinfo note = noteService.deleteNotes(token, notes.getNid());
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("203"),note,200));
		
		
	}

	/*
	 * API for pin a Note
	 */
	@PutMapping("/pin/{token}")
	public ResponseEntity<NoteResponse> pin(@RequestBody PinDto pin, @PathVariable String token) {
		  Noteinfo note = noteService.pinNote(pin.getNid(), token);
		   return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("205"),note,200));
	}

	@PutMapping("/unpin/{token}")
	public ResponseEntity<NoteResponse> unpin(@RequestBody PinDto pin, @PathVariable String token) {
		  Noteinfo note = noteService.unpinNote(pin.getNid(), token);
		   return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("205"),note,200));
	}
	
	/* API for archieve a Note */
	@PutMapping("/archieve/{token}")
	public ResponseEntity<NoteResponse> archieve(@RequestBody TrashNotes archieve, @PathVariable String token) {
		    Noteinfo note = noteService.archieveNote(archieve.getNid(), token);
		    return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("206"),note,200));
	}

	/* API for archieve a Note */
	@PutMapping("/unarchieve/{token}")
	public ResponseEntity<NoteResponse> unarchieve(@RequestBody TrashNotes archieve, @PathVariable String token) {
		    Noteinfo note = noteService.unarchieveNote(archieve.getNid(), token);
		    return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("206"),note,200));
	}
	/*
	 * API for updating color to a Note
	 */
	@PutMapping("/color/{token}")
	public ResponseEntity<NoteResponse> addColour(@RequestBody com.bridgelabz.fundoonotesBackend.dto.color color,@PathVariable String token) {
		   String note = noteService.addColour(color.getNid(), token, color.getColour());
		   return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("207"),note,200));

	}

	/*
	 *  API for getting all archieve notes Notes 
	 */
	@GetMapping("/getAllArchieve/{token}")			
	public ResponseEntity<NoteResponse> getArchieve(@PathVariable String token) {
		  List<Noteinfo> note = noteService.getarchieved(token);
		  return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("206"),note,200));
	}

	
	/**
	 * API for getting all trashed Notes
	 */
	@GetMapping("/getAlltrashed/{token}")
	public ResponseEntity<NoteResponse> getTrashed(@PathVariable String token) {
	        List<Noteinfo> note = noteService.getAlltrashednotes(token);
	        return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("203"),note,200));
	}

	/**
	 * API for getting all Pinned Notes
	 */
	@GetMapping("/getAllPinned/users/{token}")
	public ResponseEntity<NoteResponse> getPinned(@PathVariable String token) {
	        List<Noteinfo> note = noteService.getAllPinneded(token);
	        return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("205"),note,200));
	}

	/*
	 * API for adding remainder to Notes
	 */
	@PostMapping("/addremainder/{token}")
	public ResponseEntity<NoteResponse> addRemainder(@PathVariable String token,@RequestBody ReminderDto remainder) {
		//System.out.println(remainder.getRemainder()+"**********");
		     String note = noteService.addReminder(remainder.getNid(), token, remainder);
		     return ResponseEntity.status(HttpStatus.CREATED)
						.body(new NoteResponse(env.getProperty("206"),note,200));
	}

	/*
	 * API for removing remainder Notes
	 */
	@PutMapping("/removeRemainder/{token}")
	public ResponseEntity<NoteResponse> removeRemainder(@PathVariable String token, @RequestBody TrashNotes noteId) {
		     String note = noteService.removeReminder(noteId, token);
		      return ResponseEntity.status(HttpStatus.CREATED)
						.body(new NoteResponse(env.getProperty("206"),note,200));
	}
	
	@GetMapping("/searchTitle")
	public List<Noteinfo> searchTitle(@RequestParam String title) throws IOException {
		//return esService.searchByTitle(title, token);
		List<Noteinfo> data =null;
		try {
			data = esService.getNoteByTitleAndDescription(title);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return data;
	}
	
	/*
	 * API to Sort the Note Details By Title in ascending order
	 */

	@GetMapping(value = "/notes/ascendingSortByTitle")
	public ResponseEntity<NoteResponse> ascSortByNoteTitle() {
		List<String> note = noteService.ascSortByName();
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("212"),note,200));
	}
	
	/*
	 * API to Sort the Note Details By Title in descending order
	 */

	@GetMapping(value = "/notes/descSortByTitle")
	public ResponseEntity<NoteResponse> descSortByNoteTitle() {
		List<Noteinfo> note = noteService.sortByName();
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("213"),note,200));
	}
	
	/*
	 * API to Sort the Label Details By Name
	 */

	@GetMapping(value = "/ascendingSortByName")
	public ResponseEntity<NoteResponse> ascSortByLabelName() {
		List<String> note = labelService.ascsortByName();
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("212"),note,200));
	}

	

	/*
	 * API to Sort the Label Details By Name
	 */

	@GetMapping(value = "/descSortByName")
	public ResponseEntity<NoteResponse> descSortByLabelName() {
		List<String> note = labelService.sortByName();
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("213"),note,200));
	}
	/*
	 * API to get The All Note Details
	 */

	@GetMapping("/details")
	public List<Noteinfo> getAllNotes() {

		return noteService.getAllNotes();
	}

//	 @GetMapping(value = "/title/{text}")
//	    public List<Noteinfo> searchName(@PathVariable final String text) {
//	        return result.findByName(text);
//	    }
	 
}
