package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
/*#
 * Description: implementation part for user when user register,login,update
 * @author : SaiKiranMsk
 *     
 */
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.Update;
import com.bridgelabz.fundoo_note_api.dto.UserLogin;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.UserException;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.UserService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;
import com.bridgelabz.fundoo_note_api.utility.MailService;


@Service
public class UserImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncode;
	@Autowired
	private JwtGenerator generate;
	
	@Autowired
	private JavaMailSenderImpl senderimp;

	
	@Override
	public User login(UserLogin userdto) {
	
	    Optional<User> user = userRepository.findUserByEmail(userdto.getUserEmail());
	    User userDetails=new User();
	    if(user.isPresent()) {
	    	BeanUtils.copyProperties(userdto, userDetails);
	    	if ((userDetails.getIsVerified().equals("true") && (passwordEncode.matches(userDetails.getPassword(),userdto.getPassword())))) 
	    	{
	    		/* send the email verification to the register user */

				this.mailservice();
				MailService.senMail(user, senderimp, generate.jwtToken(userDetails.getId()));

	    	}
	    }
			
		return null;
	}
	
	
	
//	@Transactional
//	@Override
//	public User login(String token) {
//
//		/* with using the query */
//
//		// BeanUtils.copyProperties(userDto,User.class);
//		int id = (Integer) generate.parseJWT(token);
//		User user = userRepository.getUserById(id);
//		if (user != null) {
//			return user;
//		}
//
//		/* without using the query */
//
//		// Userlogin userlogindto = new Userlogin();
//		// List<UserRecord> list = this.getUsers(userRecord);
//		//
//		// for (UserRecord ls : list) {
//		
//		// if (ls.getId() == id&&config.passwordEncoder().matches(userRecord.getPassword(),
//		// ls.getPassword())) {
//		// userlogindto.setPassword(ls.getName());
//		// }
//		//
//		// return ls;
//		// }
//		// }
//		return null;
//
//	}

	@SuppressWarnings("unused")
	@Transactional
	@Override
	public User register(Register userDto) {

		Optional<User> useremail = userRepository.findUserByEmail(userDto.getEmail());
		if(useremail.isPresent()) 
			throw new UserException(208,"user already Exist");
		try {
			if (useremail == null) {
				/* using the modelMapper and getting the User */

				User user=new User();
				BeanUtils.copyProperties(userDto, user);

				/* setting the password as encrypted */
			    user.setPassword(passwordEncode.encode(userDto.getPassword()));
				user.setDate(LocalDateTime.now());
				user.setIsVerified("false");
				User result = userRepository.save(user);

				/* send the email verification to the register user */

				this.mailservice();
				MailService.senMail(user, senderimp, generate.jwtToken(user.getId()));

				return result;
			}
		} catch (Exception ae) {
			throw new UserException("user Not registered");
		}
		return null;
	}

	@Transactional
	@Override
	public Boolean verify(String token) {

		int id = (Integer) generate.parseJWT(token);
		User user = userRepository.getUserById(id);
		System.out.println(user.getName());
		user.setIsVerified("true");
		User users = userRepository.save(user);
		if (users != null) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public User forgotPassword(Update userDto) {
	
			Optional<User> userEmail = userRepository.findUserByEmail(userDto.getEmail());
			if (userEmail.isPresent())
				throw new UserException(404,"user not found");
			User use = new User();
			BeanUtils.copyProperties(userDto, use);
			use.setPassword(passwordEncode.encode(use.getPassword()));
			return userRepository.save(use);
	}

	@Transactional
	@Override
	public List<User> getUsers() {
		List<User> ls = new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;
	}

	@Transactional
	@Override
	public User removeUser(String token) {
		try {
			int id = (Integer) generate.parseJWT(token);
			List<User> list = this.getUsers();
			list.stream().filter(t -> t.getId() == id).forEach(t -> {
				userRepository.delete(t);
				System.out.println("delete" + t);
			});
		} catch (Exception ae) {
			throw new UserException("user Not removed");
		}
		return null;
		// for (User ls : list) {
		// if (ls.getId() == id) {
		// userRepository.delete(ls);
		//
		// }
		// }
	}

	public JavaMailSenderImpl mailservice() {
		senderimp.setUsername(System.getenv("email"));
		senderimp.setPassword(System.getenv("password"));
		senderimp.setPort(587);
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable","true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		senderimp.setJavaMailProperties(prop);
		return senderimp;
	}

	
}