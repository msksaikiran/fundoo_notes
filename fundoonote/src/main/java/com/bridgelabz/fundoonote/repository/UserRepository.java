package com.bridgelabz.fundoonote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.entity.Noteinfo;
import com.bridgelabz.fundoonote.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	@Query(value = "select * from user where uid=?", nativeQuery = true)
    String login();

	@Query(value = "insert into user (date, email, is_verified, name, number, password, id) values (?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
	User register(User user);

	@Query(value = "update user set password=? where email=?", nativeQuery = true)
	User forgotPassword(String password, String email);

	@Query(value = "select * from user where uid=?", nativeQuery = true)
	Optional<User> getUserById(long id);

	@Query(value = "select * from user where email=?", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

	@Query(value = "update user set is_verified=true where uid=?", nativeQuery = true)
	boolean verify(long id);

	@Query(value = "select * from user_collablare  where user_uid=?" , nativeQuery = true)
	List<User>  getCollobaraterById(long user_uid);

	@Query(value = "select * from user  where user_uid=?" , nativeQuery = true)
	List<Noteinfo> findNotesByuserId(long userId);

	@Query(value = "select * from noteinfo where user_id=?", nativeQuery = true)
	List<Noteinfo> findNoteByUserId(int id);

	@Query(value="select * from user where uid=?",nativeQuery=true)
	Optional<User> findUserByProfile(long id);
}
