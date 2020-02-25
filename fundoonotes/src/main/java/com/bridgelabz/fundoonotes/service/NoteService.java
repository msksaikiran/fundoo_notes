package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.Noteinfo;

public interface NoteService {

	public List<Noteinfo> getAllNotes();

	public void addNotes(Noteinfo notes);

	public Noteinfo getNotes(int id);

	public void removeNotes(Noteinfo notes, String id);

}