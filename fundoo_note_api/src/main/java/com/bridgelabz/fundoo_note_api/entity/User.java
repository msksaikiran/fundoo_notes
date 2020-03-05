package com.bridgelabz.fundoo_note_api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private LocalDateTime date;
	@Column
	private String isVerified;
	@Column
	private long number;

//	@OneToMany(mappedBy = "user")
//	private List<Noteinfo> note = new ArrayList<>();

	@ManyToMany
	
	        //@JoinColumn(name="Email_ID", referencedColumnName="id")
	   
	//@JoinTable(name= "Collabarate",joinColumns = {@JoinColumn (name="note_id")},inverseJoinColumns = {@JoinColumn (name="user_id")})
	private List<Noteinfo> collablare;
	
	/* default constructor */

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "UserRecord [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

	public List<Noteinfo> getCollablare() {
		return collablare;
	}

	public void setCollablare(List<Noteinfo> i) {
		this.collablare = i;
	}

}
