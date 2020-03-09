package com.bridgelabz.fundoo_note_api.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Noteinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long nid;
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
	
	
	@ManyToMany(targetEntity = Label.class)
	public List<Label> label;
	
	
	public long getNid() {
		return nid;
	}
	public void setNid(long nid) {
		this.nid = nid;
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

//	@ManyToOne
//	private User user;

//	@ManyToMany(targetEntity = Label.class)
//	private List<Label> lable;

	

}
