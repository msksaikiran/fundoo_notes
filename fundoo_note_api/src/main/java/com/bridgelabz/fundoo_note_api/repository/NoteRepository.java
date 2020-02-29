package com.bridgelabz.fundoo_note_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;

@Repository
public interface NoteRepository extends CrudRepository<Noteinfo, Integer> 
{

	

	@Query(value = "select * from noteinfo where note_id=?", nativeQuery = true)
	Noteinfo findNoteById(int id);

	@Query(value = "select * from noteinfo where user_id=?", nativeQuery = true)
	List<Noteinfo> findNoteByUserId(int parseInt);  

	@Query(value = "delete from noteinfo where noteId=?", nativeQuery = true)
	Noteinfo removeNotes(int parseInt);  
}  