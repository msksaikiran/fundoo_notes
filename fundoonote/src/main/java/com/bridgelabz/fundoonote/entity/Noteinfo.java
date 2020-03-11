package com.bridgelabz.fundoonote.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<Label> label;
	

}
