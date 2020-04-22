package com.bridgelabz.fundoonotesBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesBackend.dto.EmailVeify;
import com.bridgelabz.fundoonotesBackend.dto.Register;
import com.bridgelabz.fundoonotesBackend.dto.UpdatePassword;
import com.bridgelabz.fundoonotesBackend.dto.UserLogin;
import com.bridgelabz.fundoonotesBackend.entity.User;

public interface UserService {

	String login(UserLogin user);

	User register(Register userRecord);

	User forgotPassword(String newPassword, String token);

	void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token);

	void deleteFileFromS3Bucket(String fileName,String token);

	Boolean verify(String token);

	String emailVerify(EmailVeify email);

    User getUser(String token, boolean isCacheable);

	User getImageUrl(String token);

	ArrayList<User> getUserByNoteid(Long nid);


}