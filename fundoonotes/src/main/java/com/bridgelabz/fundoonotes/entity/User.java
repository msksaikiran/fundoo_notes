package com.bridgelabz.fundoonotes.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String password;
	private Date date;
	private String isVerified;
	private long number;

	@OneToMany(cascade = CascadeType.ALL)

	@JoinColumn(name = "id")
	 private List<Noteinfo> note;
	
    //default constructor
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public List<Noteinfo> getNote() {
		return note;
	}

	public void setNote(List<Noteinfo> note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "UserRecord [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

}
