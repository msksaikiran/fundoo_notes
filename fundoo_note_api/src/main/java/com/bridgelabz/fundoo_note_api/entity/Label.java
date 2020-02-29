package com.bridgelabz.fundoo_note_api.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
	private String lableName;
	private int userId;
	
	@OneToMany(mappedBy = "lable")
    private List<Noteinfo> note;

	public int getLableId() {
		return labelId;
	}

	public void setLableId(int lableId) {
		this.labelId = lableId;
	}

	public String getName() {
		return lableName;
	}

	public void setName(String name) {
		this.lableName = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId2) {
		this.userId = userId2;
	}
	
	
}
