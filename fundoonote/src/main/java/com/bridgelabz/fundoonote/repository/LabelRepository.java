package com.bridgelabz.fundoonote.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.entity.Label;

@Repository
public interface LabelRepository extends CrudRepository<Label, Integer> {

	@Query(value = "select * from label where l_id=?", nativeQuery = true)
	Label findLableById(long id);

	@Query(value = "select * from label where user_id=?", nativeQuery = true)
	Set<Label> findLableByUserId(long user_id);

	@Query(value = "select * from label where lable_name=?", nativeQuery = true)
	Label findLableByName(String labelName);

}
