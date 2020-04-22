package com.bridgelabz.fundoonotesBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesBackend.entity.Label;
import com.bridgelabz.fundoonotesBackend.entity.User;

@Repository
public interface LabelRepository extends CrudRepository<Label, Integer> {

	@Query(value = "select * from label where l_id=?", nativeQuery = true)
	Optional<Label> findLableById(long id);

	@Query(value = "select * from label where user_id=?", nativeQuery = true)
	List<Label> findLableByUserId(long user_id);

	@Query(value = "select * from label where lable_name=?", nativeQuery = true)
	Optional<Label> findLableByName(String labelName);

	@Query(value="select note_nid from noteinfo_label where label_l_id=?",nativeQuery = true)
	List<Long> findLabelNote(long lId);

	//Optional<User> findLableByname(String lname);

	
}
