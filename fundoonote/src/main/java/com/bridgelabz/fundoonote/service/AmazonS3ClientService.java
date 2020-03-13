package com.bridgelabz.fundoonote.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3ClientService
{
	void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token);

    void deleteFileFromS3Bucket(String fileName);
}