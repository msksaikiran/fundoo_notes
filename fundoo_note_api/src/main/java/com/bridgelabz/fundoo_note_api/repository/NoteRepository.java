package com.bridgelabz.fundoo_note_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.fundoo_note_api.entity.Noteinfo;


public interface NoteRepository extends CrudRepository<Noteinfo, Integer> 
{

	@Query(value = "select * from fundoosai.noteinfo where user_id=?", nativeQuery = true)
	List<Noteinfo> findAllById(int noteId);  

}  