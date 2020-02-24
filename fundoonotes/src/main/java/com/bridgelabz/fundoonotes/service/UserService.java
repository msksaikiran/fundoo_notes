package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ConfigurFile;
import com.bridgelabz.fundoonotes.controller.UserController;
import com.bridgelabz.fundoonotes.dto.Userlogin;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private ConfigurFile config;
	
	public List<UserRecord> getUsers(UserRecord userRecord) {
		List<UserRecord> ls = new ArrayList<>();
		//userRecords.setPassword(config.passwordEncoder().(userRecords.getPassword()));
		userRepository.findAll().forEach(ls::add);
		//config.passwordEncoder().matches(userRecord.getPassword(),ls);
		return ls;
	}

	public void addUser(UserRecord notes) {
		notes.setPassword(config.passwordEncoder().encode(notes.getPassword()));
		userRepository.save(notes);
	}

	public UserRecord getUser(int id,UserRecord userRecord) {
		Userlogin userlogindto = new Userlogin();
		List<UserRecord> list = this.getUsers(userRecord);
		// list.stream().filter((t) -> t.getId() == id);
		for (UserRecord ls : list) {
			if (ls.getId() == id) {
				userlogindto.setEmail(ls.getEmail());
				config.passwordEncoder().matches(userRecord.getPassword(), ls.getPassword());
				userlogindto.setPassword(ls.getName());
				return ls;
			}
		}
		return null;

	}

	public void removeUser(UserRecord userRecord, String name) {
		List<UserRecord> list = this.getUsers(userRecord);
		for (UserRecord ls : list) {
			if (ls.getName().equalsIgnoreCase(name)) {
				userRepository.delete(ls);

			}
		}

		// userRepository.delete(userRecord);
	}

}