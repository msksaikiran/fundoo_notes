package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.fundoonotes.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query(value="select * from fundoosai.user where id=?",nativeQuery=true)
	public String login();
	
	@Query(value="insert into user (date, email, is_verified, name, number, password, id) values (?, ?, ?, ?, ?, ?, ?)",nativeQuery=true)
    public User register(User user);
	
	@Query(value = "update user set password=? where email=?", nativeQuery = true)
	public User forgotPassword(User user);
	
	@Query(value = "select * from fundoosai.user where id=?", nativeQuery = true)
	public User getUserById(int id);
	
	@Query(value="select * from fundoosai.user where email=?",nativeQuery=true)
	public User getUserByEmail(String email);

	@Query(value="update fundoosai.user set is_verified=true where id=?",nativeQuery=true)
	public void verify(Integer id);

}
