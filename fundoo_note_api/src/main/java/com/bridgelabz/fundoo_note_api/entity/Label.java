package com.bridgelabz.fundoo_note_api.entity;

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

@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column
	private String lableName;
	@Column
	private int userId;
	@Column
	private LocalDateTime UpdateDateAndTime;

//	@ManyToMany(targetEntity = Noteinfo.class)
	
	//private List<Noteinfo> note ;

	public int getLabelId() {
		return Id;
	}

	public void setLabelId(int labelId) {
		this.Id = labelId;
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

	@Override
	public String toString() {
		return "Label [Id=" + Id + ", lableName=" + lableName + ", userId=" + userId + ", UpdateDateAndTime="
				+ UpdateDateAndTime + "]";
	}

	
}
