package com.bridgelabz.fundoonotes.serviceImplentation;

import java.util.List;


import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoonotes.configuration.ApplicationConfiguration;
import com.bridgelabz.fundoonotes.dto.Login;
import com.bridgelabz.fundoonotes.dto.Register;
import com.bridgelabz.fundoonotes.dto.Update;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

@Service
public class UserImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationConfiguration config;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObject;
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	@Override
	public User login(int id, Login userDto) {

		/* with using the query */

		BeanUtils.copyProperties(userDto,User.class);
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
	
	@Transactional
	@Override
	public User register(Register userDto) {

		User useremail = userRepository.getUserByEmail(userDto.getEmail());
		if(useremail==null){
		userDto.setPassword(config.passwordEncoder().encode(userDto.getPassword()));
		userDto.setDate(new Date(System.currentTimeMillis()));
		User user = (User)modelMapper.map(userDto, User.class);
		user.setIsVerified("false");
		User result = userRepository.save(user);
		
		String mailResponse = response.fromMessage("http://localhost:8081/verify",generate.jwtToken(user.getId()));
		mailObject.setEmail(user.getEmail());
		mailObject.setMessage(mailResponse);
		mailObject.setSubject("verified");
		System.out.println(mailResponse);
		MailServiceProvider.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
		System.out.println(generate.jwtToken(user.getId()));
		return result;
		}
		return null;
	}
	
	@Transactional
	@Override
	public Boolean verify(String token) {
		System.out.println("id is in verification" + (Integer) generate.parseJWT(token));
		Integer id = (Integer) generate.parseJWT(token);
		userRepository.verify(id);
		return true;
	}
	
	@Transactional
	@Override
	public User forgotPassword(String token,Update userDto) {
		Integer id = null;
		User user = (User)modelMapper.map(userDto, User.class);
        id = (Integer) generate.parseJWT(token);
        User ls = userRepository.getUserById(id);
		User update=null;
		if(ls!=null){
			if (ls.getEmail().equalsIgnoreCase(userDto.getEmail())) {
				ls.setPassword(config.passwordEncoder().encode(user.getPassword()));
				update=userRepository.save(ls);
				return update;
			}
		}
		return null;
	}

	@Transactional
	@Override
	public List<User> getUsers(User userRecord) {
		List<User> ls = new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;
	}

    @Transactional
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
}