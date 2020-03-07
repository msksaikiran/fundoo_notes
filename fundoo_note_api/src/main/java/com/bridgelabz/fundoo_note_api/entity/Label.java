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
	private int userId;
	@Column
	private LocalDateTime UpdateDateAndTime;

    	@ManyToMany(targetEntity = Noteinfo.class)
	    private List<Noteinfo> note ;
	
}
