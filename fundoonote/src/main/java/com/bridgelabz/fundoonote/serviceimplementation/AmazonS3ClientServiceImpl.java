package com.bridgelabz.fundoonote.serviceimplementation;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.fundoonote.entity.User;
import com.bridgelabz.fundoonote.exception.UserException;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.service.AmazonS3ClientService;
import com.bridgelabz.fundoonote.utility.JwtGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService{
    private String awsS3AudioBucket;
    
    private AmazonS3 amazonS3;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtGenerator generate;
	
	@Autowired
	 private Environment env;


    @Autowired
    public AmazonS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket) 
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }

    @Async
    public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token) 
    {
    	long id = (Long) generate.parseJWT(token);
    	
    		User user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));

    	
        String fileName = multipartFile.getOriginalFilename();

        user.setProfile(fileName+" uploaded");
        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();

            PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
            userRepository.save(user);
        } catch (IOException | AmazonServiceException ex) {
        	throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
        }
    }

    @Async
    public void deleteFileFromS3Bucket(String fileName,String token) 
    {
    	long id = (Long) generate.parseJWT(token);
    	
		User user = userRepository.getUserById(id)
			.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));

		 user.setProfile("null");
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
        } catch (AmazonServiceException ex) {
        	throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
        }
        userRepository.save(user);
    }
}