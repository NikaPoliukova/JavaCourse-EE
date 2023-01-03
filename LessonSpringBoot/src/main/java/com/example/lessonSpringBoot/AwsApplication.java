package com.example.lessonSpringBoot;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsApplication {
  public static final String AWS_REGION = "us-east-1";
  public static final String S3_ENDPOINT = "http://localhost:4566";

  public static void main(String[] args) {

      BasicAWSCredentials credentials = new BasicAWSCredentials("foo", "bar");
      AwsClientBuilder.EndpointConfiguration client =
          new AwsClientBuilder.EndpointConfiguration(S3_ENDPOINT, AWS_REGION);
      AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
          .withEndpointConfiguration(client)
          .withPathStyleAccessEnabled(true)
          .withCredentials(new AWSStaticCredentialsProvider(credentials));
       final AmazonS3 clientS3 = builder.build();

    clientS3.createBucket("imgbucket");
    clientS3.getBucketAcl("imgbucket");

  }
}
