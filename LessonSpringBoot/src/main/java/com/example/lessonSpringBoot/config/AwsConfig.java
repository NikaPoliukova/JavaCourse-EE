package com.example.lessonSpringBoot.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
  public final String AWS_REGION = "us-east-1";
  public final String S3_ENDPOINT = "http://localhost:4566";


  @Bean
  AmazonS3 amazonS3client() {
    BasicAWSCredentials credentials = new BasicAWSCredentials("foo", "bar");
    AwsClientBuilder.EndpointConfiguration client = new AwsClientBuilder.EndpointConfiguration(S3_ENDPOINT, AWS_REGION);
    AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(client)
        .withPathStyleAccessEnabled(true)
        .withCredentials(new AWSStaticCredentialsProvider(credentials));
    return builder.build();
  }
}
