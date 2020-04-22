package com.bridgelabz.fundoonotesBackend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;

@Service
public interface IServiceElasticSearch {
	public String createNote(Noteinfo note) throws IOException;

	public Noteinfo findById(String id) throws Exception;

	public String upDateNote(Noteinfo note) throws Exception;

	public String deleteNote(String id) throws IOException;

//	List<Noteinfo> searchByTitle(String title, String token) throws IOException;
//
//	List<Noteinfo> searchByTechnology(String technology) throws Exception;
	 List<Noteinfo> getNoteByTitleAndDescription(String text);
}