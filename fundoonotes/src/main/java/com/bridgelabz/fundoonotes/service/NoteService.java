package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Noteinfo;

public interface NoteService {

	public List<Noteinfo> getAllNotes();

	public void addNotes(Noteinfo notes);

	public Noteinfo getNotes(int id);

	public void removeNotes(Noteinfo notes, String id);

}