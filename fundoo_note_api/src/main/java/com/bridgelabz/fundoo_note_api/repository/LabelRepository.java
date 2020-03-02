package com.bridgelabz.fundoo_note_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoo_note_api.entity.Label;

@Repository
public interface LabelRepository extends CrudRepository<Label, Integer> {

	@Query(value = "select * from label where label_id=?", nativeQuery = true)
	Label findLableById(int id);

	@Query(value = "select * from label where user_id=?", nativeQuery = true)
	List<Label> findLableByUserId(int id);

}
