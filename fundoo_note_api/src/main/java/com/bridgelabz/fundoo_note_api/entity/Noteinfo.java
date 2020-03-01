package com.bridgelabz.fundoo_note_api.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Noteinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noteId;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private int isArchieved;
	@Column
	private int isPinned;
	@Column
	private int isTrashed;
	@Column
	private LocalDateTime createdDateAndTime;
	@Column
	private LocalDateTime upDateAndTime;
	@Column
	private String colour;
	@Column
	private LocalDateTime reminder;

	@ManyToOne
    private User user;
	
	@ManyToOne
    private Label lable;
	

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

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

	public int getIsArchieved() {
		return isArchieved;
	}

	public void setIsArchieved(int isArchieved) {
		this.isArchieved = isArchieved;
	}

	public int getIsPinned() {
		return isPinned;
	}

	public void setIsPinned(int isPinned) {
		this.isPinned = isPinned;
	}

	public int getIsTrashed() {
		return isTrashed;
	}

	public void setIsTrashed(int isTrashed) {
		this.isTrashed = isTrashed;
	}

	public LocalDateTime getCreatedDateAndTime() {
		return createdDateAndTime;
	}

	public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
		this.createdDateAndTime = createdDateAndTime;
	}

	public LocalDateTime getUpDateAndTime() {
		return upDateAndTime;
	}

	public void setUpDateAndTime(LocalDateTime upDateAndTime) {
		this.upDateAndTime = upDateAndTime;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Noteinfo [noteId=" + noteId + ", title=" + title + ", description=" + description + ", isArchieved="
				+ isArchieved + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed + ", createdDateAndTime="
				+ createdDateAndTime + ", upDateAndTime=" + upDateAndTime + ", colour=" + colour + ", reminder="
				+ reminder + ", user=" + user + ", lable=" + lable + "]";
	}

	
	
}
