package com.bridgelabz.fundoonote.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonote.dto.LableDto;
import com.bridgelabz.fundoonote.dto.NoteDto;
import com.bridgelabz.fundoonote.dto.UpdateLabel;
import com.bridgelabz.fundoonote.entity.Label;
import com.bridgelabz.fundoonote.response.LabelResponse;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelController {

	@Autowired
	private LabelService labelService;

	@Autowired
	private Environment env;
	/*
	 * API to add the Label Details
	 */
	@PostMapping(value = "/{token}/notes")
	public ResponseEntity<LabelResponse> createLabel(@RequestBody LableDto label, @PathVariable String token) {

		Label labels = labelService.createLable(label, token);
		
			 return ResponseEntity.status(HttpStatus.CREATED)
						.body(new LabelResponse(env.getProperty("300"),200,labels));
	}

	/*
	 * API to add the  Notes To Label 
	 */
	@PostMapping(value = "/{lid}/{token}")
	public ResponseEntity<LabelResponse> addNotesToLabel(@RequestBody NoteDto label, @PathVariable String token,@PathVariable long lid) {

		Label lnote = labelService.addNotesToLabel(label, token,lid);
		
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponse(env.getProperty("301"),200,lnote));
	}
	

	/*
	 * API to add the Existing notes to Label Details
	 */
	@PostMapping(value = "/{lid}/{token}/{noteId}")
	public ResponseEntity<Response> addExistingNotesToLabel(long noteId, String token, long labelId) {
		boolean label = labelService.addExistingNotesToLabel(noteId, token, labelId);
		if(label)
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response(env.getProperty("301"),200));
		return null;

	}
	
	/*
	 * API to add the update Label Details
	 */
	@PutMapping(value = "/{id}/users/{token}")
	public ResponseEntity<LabelResponse> updateLabel(@PathVariable String token,@PathVariable long id, @RequestBody UpdateLabel dto) {
		Label label = labelService.updateLabel(token,id, dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("302"),200,label));
	}
	
	/*
	 * API to add the delete Label Details
	 */
	@DeleteMapping(value = "/{id}/{token}")
	public ResponseEntity<LabelResponse> deleteLabel(@PathVariable long id,@PathVariable String token) {
	     Label label = labelService.removeLabel(token,id);
	     if(label!=null)
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("304"),200,label));
	     else
	    	 return ResponseEntity.status(HttpStatus.CREATED)
	 				.body(new LabelResponse("label Not Deleted",200,label));
	}
	
	/*
	 * API to getting the Label Details By Id
	 */

	@GetMapping(value = "/{id}")
	public ResponseEntity<LabelResponse> getLabel(@PathVariable long id) {
		Label label = labelService.getLableById(id);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("303"),200,label));
	}
	
	
	/*
	 * API to get The All label Details
	 */

	@GetMapping("/deatils")
	public List<Label> getAllLables() {
		return labelService.getAllLables();
	}

		
	/*
	 * API to getting the Label Details By User_Id
	 */

	@GetMapping(value = "/user/{token}")
	public ResponseEntity<LabelResponse> getLabelByUserId(@PathVariable String token) {
		Set<Label> label = labelService.getLableByUserId(token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("302"),200,label));
	}
	/*
	 * API to deleting the Note Details By _Id
	 */

}
