package com.example.users.service.amazonService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class AwsService {

  private final AmazonS3 client;
  @Value("${aws.image-placeholder-path}")
  private String placeholderPath;

  public void uploadFile(InputStream stream, String fileName) {
    PutObjectRequest request = new PutObjectRequest("imgbucket", fileName, stream, new ObjectMetadata());
    client.putObject(request);
  }

  public URI getImagePath(String imageName) throws URISyntaxException {
    if (client.doesObjectExist("imgbucket", imageName)) {
      GetObjectRequest request = new GetObjectRequest("imgbucket", imageName);
      return client.getObject(request).getObjectContent().getHttpRequest().getURI();
    }
    return new URI(placeholderPath);
  }

  public void deleteImage(String oldImage) {
    DeleteObjectRequest request = new DeleteObjectRequest("imgbucket", oldImage);
    client.deleteObject(request);
  }
}