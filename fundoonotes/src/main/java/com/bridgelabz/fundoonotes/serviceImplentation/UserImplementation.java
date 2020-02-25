package com.bridgelabz.fundoonotes.serviceImplentation;

import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.configuration.ConfigurFile;
import com.bridgelabz.fundoonotes.dto.Userlogin;
import com.bridgelabz.fundoonotes.model.UserRecord;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class UserImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConfigurFile config;
	@Autowired
	private JwtGenerator generate;

	public List<UserRecord> getUsers(UserRecord userRecord) {
		List<UserRecord> ls = new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;
	}

	public void addUser(UserRecord user) {
		user.setPassword(config.passwordEncoder().encode(user.getPassword()));
		user.setDate(new Date(System.currentTimeMillis()));
		userRepository.save(user);
		System.out.println(generate.jwtToken(user.getId()));
	}

	public UserRecord getUser(int id, UserRecord userRecord) {
		Userlogin userlogindto = new Userlogin();
		List<UserRecord> list = this.getUsers(userRecord);

		for (UserRecord ls : list) {
			if (ls.getId() == id) {
				if (config.passwordEncoder().matches(userRecord.getPassword(), ls.getPassword())) {
					userlogindto.setPassword(ls.getName());
				}

				return ls;
			}
		}
		return null;

	}

	public void removeUser(UserRecord userRecord, String id) {
		int idd = Integer.parseInt(id);
		List<UserRecord> list = this.getUsers(userRecord);
		for (UserRecord ls : list) {
			if (ls.getId() == idd) {
				userRepository.delete(ls);

			}
		}
	}

}