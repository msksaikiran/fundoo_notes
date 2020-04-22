package com.bridgelabz.fundoonotesBackend.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesBackend.dto.LableDto;
import com.bridgelabz.fundoonotesBackend.dto.NoteDto;
import com.bridgelabz.fundoonotesBackend.dto.label;
import com.bridgelabz.fundoonotesBackend.entity.Label;
import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.response.LabelResponse;
import com.bridgelabz.fundoonotesBackend.response.Response;
import com.bridgelabz.fundoonotesBackend.service.LabelService;

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
	@PostMapping(value = "/create/{token}")
	public ResponseEntity<LabelResponse> createLabel(@RequestBody LableDto label, @PathVariable String token) {

		Label labels = labelService.createLable(label, token);
		
			 return ResponseEntity.status(HttpStatus.CREATED)
						.body(new LabelResponse(env.getProperty("300"),200,labels));
	}

	/*
	 * API to add the  Notes To Label 
	 */
	@PostMapping(value = "/addlabels/{token}")
	public ResponseEntity<LabelResponse> addLabelToNotes(@RequestBody label label, @PathVariable String token) {

		Label lnote = labelService.addLabelToNotes(label.getnId(),label.getLname(),token);
		
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponse(env.getProperty("301"),200,lnote));
	}
	

	@PostMapping(value = "/removelabels/{token}")
	public ResponseEntity<LabelResponse> removeLabelToNotes(@RequestBody label label, @PathVariable String token) {

		Label lnote = labelService.removeLabelToNotes(label.getnId(),label.getLname(),token);
		
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponse(env.getProperty("301"),200,lnote));
	}
	
	@GetMapping(value = "/getlabelNotes/{lId}")
	public ResponseEntity<LabelResponse> getLabelNotes(@PathVariable long lId) {

		ArrayList<Noteinfo> lnote = labelService.LabelNote(lId);
		
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponse(env.getProperty("301"),200,lnote));
	}
	
	/*
	 * API to add the update Label Details
	 */
	@PutMapping(value = "/updatelabel/{token}")
	public ResponseEntity<LabelResponse> updateLabel(@PathVariable String token,@RequestParam("lid") long id,@RequestBody LableDto label) {
		 labelService.updateLabel(token,id,label);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("302"),200,label));
	}
	
	/*
	 * API to add the delete Label Details
	 */
	@PostMapping(value = "/delete/{token}")
	public ResponseEntity<LabelResponse> deleteLabel(@RequestParam("lid") long labelid,@PathVariable String token) {
	     Label label = labelService.removeLabel(token,labelid);
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

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<LabelResponse> getLabelName(@PathVariable long id) {
		Label label = labelService.getLableById(id);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("303"),200,label));
	}
	
	@GetMapping(value = "/details/users/{token}")
	public ResponseEntity<LabelResponse> getLabelDetails(@PathVariable String token) {
		List<Label> label = labelService.getLableDetailsByUserId(token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("302"),200,label));
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
		List<String> label = labelService.getLableByUserId(token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new LabelResponse(env.getProperty("302"),200,label));
	}
	/*
	 * API to deleting the Note Details By _Id
	 */

}
