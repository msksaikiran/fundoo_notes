package com.bridgelabz.fundoonote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.codecommit.model.UserInfo;
import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.entity.User;

@Repository
public interface NoteRepository extends CrudRepository<Noteinfo, Long> {

	@Query(value = "select * from noteinfo where nid=?", nativeQuery = true)
	Optional<Noteinfo> findNoteById(long id);

	@Query(value = "select * from noteinfo where user_id=?", nativeQuery = true)
	List<Noteinfo> findNoteByUserId(long id);

	@Query(value="select * from  noteinfo where user_id=? AND is_trashed =1",nativeQuery = true)
	List<Noteinfo> restoreNote(long userid);
	
	@Query(value="select * from  noteinfo where user_id=? AND is_archieved =1",nativeQuery = true)
	List<Noteinfo> getArchievedNotes(long userid);
	
	@Query(value="select * from  noteinfo where user_id=? AND is_pinned =1",nativeQuery = true)
	List<Noteinfo> getPinnededNotes(long userid);

	@Query(value="select collablare_nid  from user_collablare where user_uid=?",nativeQuery = true)
	List<Long> getCollabrateNotes(long userid);

//	@Query(value = "select uid from user where uid in (select user_id from noteinfo where nid=?)",nativeQuery = true)
	@Query(value = "select user_uid from user_collablare where collablare_nid=?",nativeQuery = true)
	List<Long> findcollabrateuserbyNoteId(long noteid);
	
}