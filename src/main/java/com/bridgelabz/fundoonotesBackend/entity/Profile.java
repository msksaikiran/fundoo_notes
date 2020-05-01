package com.bridgelabz.fundoonotesBackend.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Profile {

	@Id
	private String IName;

	
	public String getIName() {
		return IName;
	}

	public void setIName(String iName) {
		IName = iName;
	}
	
	
}
