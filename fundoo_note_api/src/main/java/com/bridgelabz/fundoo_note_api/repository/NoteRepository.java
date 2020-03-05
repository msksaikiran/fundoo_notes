package com.bridgelabz.fundoo_note_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoo_note_api.entity.Noteinfo;

@Repository
public interface NoteRepository extends CrudRepository<Noteinfo, Integer> {

	@Query(value = "select * from noteinfo where id=?", nativeQuery = true)
	Noteinfo findNoteById(int id);

	@Query(value = "select * from noteinfo where user_id=?", nativeQuery = true)
	List<Noteinfo> findNoteByUserId(int id);

//	List<Noteinfo> restoreNote(int userid);
//
//	List<Noteinfo> getArchievedNotes(int userid);
//
//	List<Noteinfo> getPinnededNotes(int userid);

}