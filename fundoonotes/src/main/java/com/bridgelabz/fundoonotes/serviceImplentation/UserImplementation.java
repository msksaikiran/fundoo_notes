package com.bridgelabz.fundoonotes.serviceImplentation;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.configuration.ApplicationConfiguration;
import com.bridgelabz.fundoonotes.entity.User;

import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class UserImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationConfiguration config;
	@Autowired
	private JwtGenerator generate;

	@Override
	public User login(int id, User userRecord) {

		/* with using the query */

		User user = userRepository.getUserById(id);
		if (user != null) {
			return user;
		}

		/* without using the query */

		// Userlogin userlogindto = new Userlogin();
		// List<UserRecord> list = this.getUsers(userRecord);
		//
		// for (UserRecord ls : list) {
		// if (ls.getId() == id) {
		// if (config.passwordEncoder().matches(userRecord.getPassword(),
		// ls.getPassword())) {
		// userlogindto.setPassword(ls.getName());
		// }
		//
		// return ls;
		// }
		// }
		return null;

	}
	
	@Override
	public void register(User user) {
		user.setPassword(config.passwordEncoder().encode(user.getPassword()));
		user.setDate(new Date(System.currentTimeMillis()));
		userRepository.save(user);
		System.out.println(generate.jwtToken(user.getId()));
	}
	
	@Override
	public List<User> getUsers(User userRecord) {
		List<User> ls = new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;
	}

	@Override
	public void removeUser(User userRecord, String id) {
		int idd = Integer.parseInt(id);
		List<User> list = this.getUsers(userRecord);
		for (User ls : list) {
			if (ls.getId() == idd) {
				userRepository.delete(ls);

			}
		}
	}

	@Override
	public void forgotPassword(String email,String password) {
		User update = userRepository.forgotPassword(email,password);
		if(update!=null){
			System.out.println("####");
		}
		
	}

}