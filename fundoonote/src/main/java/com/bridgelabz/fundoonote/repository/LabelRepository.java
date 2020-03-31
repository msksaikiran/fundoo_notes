package com.bridgelabz.fundoonote.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.entity.Label;

@Repository
public interface LabelRepository extends CrudRepository<Label, Integer> {

	@Query(value = "select * from label where l_id=?", nativeQuery = true)
	Optional<Label> findLableById(long id);

	@Query(value = "select * from label where user_id=?", nativeQuery = true)
	Set<Label> findLableByUserId(long user_id);

	@Query(value = "select * from label where lable_name=?", nativeQuery = true)
	Label findLableByName(String labelName);

	

}


//SELECT users.email, users.password, data.data_1, data.data_2
//FROM users,data 
//WHERE users.email='$user_email' AND users.user_id=data.user_id";