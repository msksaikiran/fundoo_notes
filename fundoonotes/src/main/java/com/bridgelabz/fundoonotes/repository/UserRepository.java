package com.bridgelabz.fundoonotes.repository;  
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.fundoonotes.model.UserRecord;  
public interface UserRepository extends CrudRepository<UserRecord, String> 
{  
	//@Query("select * from fundoo.user_record where id=:id")
	public List<UserRecord> getUserById(int id);
	public List<UserRecord> getUserByName(int id);
	
}  
