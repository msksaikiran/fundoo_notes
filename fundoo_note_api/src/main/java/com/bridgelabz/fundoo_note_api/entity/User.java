package com.bridgelabz.fundoo_note_api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long uid;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private LocalDateTime date;
	@Column
	private boolean isVerified;
	@Column
	private long number;
	
	 @OneToMany(targetEntity = Noteinfo.class,fetch = FetchType.LAZY)
	 @JoinColumn(name = "userId")
	 private List<Noteinfo> note;

	@ManyToMany
	//@JoinTable(name= "Collabarate",joinColumns = {@JoinColumn (name="note_id")},inverseJoinColumns = {@JoinColumn (name="user_id")})
	private List<Noteinfo> collablare;
	

	
}
