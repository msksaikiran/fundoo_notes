package com.bridgelabz.fundoo_note_api.dto;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class NoteDto {
	@NotNull
	private String title;
	@NotNull
	private String description;
	private String noteId;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}