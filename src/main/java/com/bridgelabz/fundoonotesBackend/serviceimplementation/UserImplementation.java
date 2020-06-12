package com.bridgelabz.fundoonotesBackend.serviceimplementation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*#
 * Description: implementation part for user when user register,login,update
 * @author : SaiKiranMsk
 *     
 */
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.codecommit.model.UserInfo;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.fundoonotesBackend.constants.Constants;
import com.bridgelabz.fundoonotesBackend.dto.EmailVeify;
import com.bridgelabz.fundoonotesBackend.dto.Mail;
import com.bridgelabz.fundoonotesBackend.dto.Register;
import com.bridgelabz.fundoonotesBackend.dto.UserLogin;
import com.bridgelabz.fundoonotesBackend.entity.Noteinfo;
import com.bridgelabz.fundoonotesBackend.entity.User;
import com.bridgelabz.fundoonotesBackend.exception.UserException;
import com.bridgelabz.fundoonotesBackend.repository.NoteRepository;
import com.bridgelabz.fundoonotesBackend.repository.UserRepository;
import com.bridgelabz.fundoonotesBackend.service.UserService;
import com.bridgelabz.fundoonotesBackend.utility.Email;
import com.bridgelabz.fundoonotesBackend.utility.JwtGenerator;
import com.bridgelabz.fundoonotesBackend.utility.MailService2;
import com.bridgelabz.fundoonotesBackend.utility.RabbitMQSender;

@Service
@PropertySource("classpath:message.properties")
public class UserImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private BCryptPasswordEncoder passEncryption;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private JavaMailSenderImpl senderimp;

	@Autowired
	private Environment env;

	@Autowired
	private MailService2 mail;

	private String awsS3AudioBucket;

	private AmazonS3 amazonS3;

	 @Autowired
	 RabbitMQSender rabbitMQSender;

	@Transactional
	@Override
	public String login(UserLogin userdto) {

		
		User user = userRepository.findUserByEmail(userdto.getEmail())
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("103")));
	
			if ((user.isVerified() == true) && passEncryption.matches(userdto.getPassword(), user.getPassword())) {
				
				String token = generate.jwtToken(user.getUid());
		        
				return token;
			} else {
				throw new UserException(HttpStatus.BAD_REQUEST, env.getProperty("105"));
			}
	}

	@Override
	public String getImageUrl(String token) {
		long id = (Long) generate.parseJWT(token);
		User user = userRepository.findUserByProfile(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("103")));
		//url.getProfile();
	 return "https://msksaikiran.s3.us-east-2.amazonaws.com/"+user.getProfile();
		
	}
	
	@Transactional
	@Override
	public User register(Register userDto) {

		User userDetails = new User();

		Optional<User> useremail = userRepository.findUserByEmail(userDto.getEmail());

		if (useremail.isPresent())
			throw new UserException(HttpStatus.ALREADY_REPORTED, env.getProperty("102"));

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
			
			String token = generate.jwtToken(userDetails.getUid());
	//		Email email =new Email();
			this.mailservice();
			mail.senEmailMail(userDetails, senderimp, token);
//			email.setEmailId(result.getEmail());
//		    email.setToken(token);
//		    
//			rabbitMQSender.sendToQueue(email);
//	        rabbitMQSender.Reciver(email);		
			 
			return result;

		} catch (Exception ae) {
			ae.printStackTrace();
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("110"));
		}
	}

	@Transactional
	@Override
	public Boolean verify(String token) {

		long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		user.setVerified(true);
		boolean users = userRepository.save(user) != null ? true : false;

		return users;
	}

	@Transactional
	@Override
	public String emailVerify(EmailVeify email) {
		User user = userRepository.findUserByEmail(email.getEmailId())
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		/*
		 * send the email verification to the register user
		 */
		String token = generate.jwtToken(user.getUid());
		this.mailservice();
		mail.senEmailMail(user, senderimp, token);
		return token;
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

	@Transactional
	@Override
	@Cacheable(value = "twenty-second-cache", key = "'tokenInCache'+#token", condition = "#isCacheable != null && #isCacheable")
	public User getUser(String token, boolean isCacheable) {
		ArrayList<User> userlist=new ArrayList<>();
		long id = (Long) generate.parseJWT(token);
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		User coluser =null;
         List<Noteinfo> colNotes = user.getCollablare();
         for(Noteinfo notes:colNotes) {
        	coluser = userRepository.findcolluserbyNoteId(notes.getNid());
        	userlist.add(coluser);
        	
         }
		return coluser;
	}
	
	@Transactional
	@Override
	public ArrayList<User> getUserByNoteid(Long nid) {
		ArrayList<User> users=new ArrayList<User>();
		List<Long> userid = noteRepository.findcollabrateuserbyNoteId(nid);
		for(Long id:userid) {
		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		users.add(user);
		}
		return users;
	}

	public JavaMailSenderImpl mailservice() {
		senderimp.setUsername("msaikiran201@gmail.com");
		senderimp.setPassword("Sai@072807");
		senderimp.setPort(587);
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		senderimp.setJavaMailProperties(prop);

		return senderimp;
	}

	@Autowired
    public void AmazonS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket) 
    { 
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }

	@Async
	public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, String token) {
		long id = (Long) generate.parseJWT(token);

		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		String fileName = multipartFile.getOriginalFilename();

		user.setProfile(fileName);
		try {
			// creating the file in the server (temporarily)
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();

			PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);
 
			if (enablePublicReadAccess) {
				putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
			}
			this.amazonS3.putObject(putObjectRequest);
			// removing the file created in the server
			file.delete();
			userRepository.save(user);
		} catch (IOException | AmazonServiceException ex) {
			ex.printStackTrace();
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
	}

	@Async
	public void deleteFileFromS3Bucket(String fileName, String token) {
		long id = (Long) generate.parseJWT(token);

		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		user.setProfile("null");
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
		} catch (AmazonServiceException ex) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
		userRepository.save(user);
	}

	

}