package com.example.lessonSpringBoot.service.amazonService;

 import com.amazonaws.services.s3.AmazonS3;
 import com.amazonaws.services.s3.AmazonS3Client;

public class AWSS3Service {
  private final AmazonS3 client;

  public AWSS3Service(){
    this(new AmazonS3Client(){
          });
  }
  public AWSS3Service(AmazonS3 client) {
    this.client = client;
  }

}

