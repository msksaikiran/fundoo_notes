package com.bridgelabz.fundoo_note_api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo_note_api.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	@Query(value="select * from user where id=?",nativeQuery=true)
	public String login();
	
	@Query(value="insert into user (date, email, is_verified, name, number, password, id) values (?, ?, ?, ?, ?, ?, ?)",nativeQuery=true)
    public User register(User user);
	
	@Query(value = "update user set password=? where email=?", nativeQuery = true)
	public User forgotPassword(String password, String email);
	
	@Query(value = "select * from user where id=?", nativeQuery = true)
	public User getUserById(int id);
	
	@Query(value="select * from user where email=?",nativeQuery=true)
	public User getUserByEmail(String email);

	@Query(value="update user set is_verified=true where id=?",nativeQuery=true)
	public void verify(int id);

}
