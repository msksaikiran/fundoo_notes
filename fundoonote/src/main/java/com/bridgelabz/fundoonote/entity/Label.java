package com.bridgelabz.fundoonote.entity;

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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
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
	
}
