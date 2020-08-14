package com.bridgelabz.fundoonotesBackend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lId;
	
	@Column
	private String lableName;

	@Column
	private LocalDateTime UpdateDateAndTime;
	
	 @ManyToMany(mappedBy = "label")
	 @JsonIgnore
     private List<Noteinfo> note;

	public long getlId() {
		return lId;
	}

	public void setlId(long lId) {
		this.lId = lId;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public LocalDateTime getUpdateDateAndTime() {
		return UpdateDateAndTime;
	}

	public void setUpdateDateAndTime(LocalDateTime updateDateAndTime) {
		UpdateDateAndTime = updateDateAndTime;
	}

	public List<Noteinfo> getNote() {
		return note;
	}

	public void setNote(List<Noteinfo> note) {
		this.note = note;
	}
	
	 
}
