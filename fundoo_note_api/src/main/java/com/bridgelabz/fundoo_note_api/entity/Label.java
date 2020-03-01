package com.bridgelabz.fundoo_note_api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int labelId;
	@Column
	private String lableName;
	@Column
	private int userId;
	@Column
	private LocalDateTime UpdateDateAndTime;
	
	@OneToMany(mappedBy = "lable")
    private List<Noteinfo> note=new ArrayList<Noteinfo>();

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getUpdateDateAndTime() {
		return UpdateDateAndTime;
	}

	public void setUpdateDateAndTime(LocalDateTime updateDateAndTime) {
		UpdateDateAndTime = updateDateAndTime;
	}	
}
