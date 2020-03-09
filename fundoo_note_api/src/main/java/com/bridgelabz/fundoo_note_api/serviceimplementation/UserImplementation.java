package com.bridgelabz.fundoo_note_api.serviceimplementation;

import java.time.LocalDateTime;
/*#
 * Description: implementation part for user when user register,login,update
 * @author : SaiKiranMsk
 *     
 */
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.UserLogin;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.exception.UserException;
import com.bridgelabz.fundoo_note_api.repository.UserRepository;
import com.bridgelabz.fundoo_note_api.service.UserService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;
import com.bridgelabz.fundoo_note_api.utility.MailService;

@Service
@PropertySource("classpath:message.properties")
public class UserImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passEncryption;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private JavaMailSenderImpl senderimp;

	 @Autowired
	 private Environment env;

	@Transactional
	@Override
	public User login(UserLogin userdto) {

		User userDetails = new User();

		User user = userRepository.findUserByEmail(userdto.getEmail())
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("103")));

		try {
			BeanUtils.copyProperties(userdto, userDetails);
			if ((user.isVerified() == true) && passEncryption.matches(userdto.getPassword(), user.getPassword())) {
				/*
				 * send the email verification to the register user
				 */
				this.mailservice();
				MailService.senMail(userDetails, senderimp, generate.jwtToken(user.getUid()));
				return user;
			}else {
				throw new UserException(HttpStatus.BAD_REQUEST,env.getProperty("105"));
			}
			
		} catch (Exception ex) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR,env.getProperty("500"));
		}
	}

	@Transactional
	@Override
	public User register(Register userDto) {

		User userDetails = new User();
		
			Optional<User> useremail = userRepository.findUserByEmail(userDto.getEmail());
			
			if (useremail.isPresent())
				throw new UserException(HttpStatus.ALREADY_REPORTED,env.getProperty("102"));

			try {
		BeanUtils.copyProperties(userDto, userDetails);

		/*
		 * setting the password as encrypted
		 */
		userDetails.setPassword(passEncryption.encode(userDto.getPassword()));
		userDetails.setDate(LocalDateTime.now());
		User result = userRepository.save(userDetails);

		/*
		 * send the email verification to the register user
		 */

		this.mailservice();
		MailService.senMail(userDetails, senderimp, generate.jwtToken(userDetails.getUid()));

		return result;

		} catch (Exception ae) {
			ae.printStackTrace();
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
	}

	@Transactional
	@Override
	public Boolean verify(String token) {

		long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));

		user.setVerified(true);
		boolean users = userRepository.save(user) != null ? true : false;

		return users;
	}

	@Transactional
	@Override
	public String emailVerify(String email) {
		User user = userRepository.findUserByEmail(email)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));
		/*
		 * send the email verification to the register user
		 */
		this.mailservice();
		MailService.senMail(user, senderimp, generate.jwtToken(user.getUid()));
		return "email sent";
	}

	@Transactional
	@Override
	public User forgotPassword(String newpassword, String token) {

		long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		user.setPassword(passEncryption.encode(newpassword));
		return userRepository.save(user);

	}

	@Override
	public User getUser(String token) {
		long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		return user;
	}

	public JavaMailSenderImpl mailservice() {
		senderimp.setUsername(System.getenv("email"));
		senderimp.setPassword(System.getenv("password"));
		senderimp.setPort(587);
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		senderimp.setJavaMailProperties(prop);
		return senderimp;
	}
	
	// @Transactional
	// @Override
	// public List<User> getUsers() {
	// List<User> ls = new ArrayList<>();
	// userRepository.findAll().forEach(ls::add);
	// return ls;
	// }

	// @Transactional
	// @Override
	// public User removeUser(String token) {
	// try {
	// int id = (Integer) generate.parseJWT(token);
	// List<User> list = this.getUsers();
	// list.stream().filter(t -> t.getId() == id).forEach(t -> {
	// userRepository.delete(t);
	// System.out.println("delete" + t);
	// });
	// } catch (Exception ae) {
	// // throw new UserException(404,"user Not removed");
	// }
	// return null;
	// }

	

}