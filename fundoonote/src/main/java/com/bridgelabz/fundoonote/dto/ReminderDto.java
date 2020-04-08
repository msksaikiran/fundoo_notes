package com.bridgelabz.fundoonote.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReminderDto {

	private String remainder;
    private long nid;
    
    
	public long getNid() {
		return nid;
	}

	public void setNid(long nid) {
		this.nid = nid;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	

//	public LocalDateTime getRemainder() {
//		return remainder;
//	}
//
//	public void setRemainder(LocalDateTime remainder) {
//		this.remainder = remainder;
//	}

	


}
