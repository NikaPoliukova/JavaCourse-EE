package com.example.lessonSpringBoot.service;

import com.example.lessonSpringBoot.repository.ImageRepository;
import com.example.lessonSpringBoot.service.amazonService.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ImageService {
  private final AwsService storageService;
  private final ImageRepository imageRepository;


  public void setNewImage(String imageName, long userId) {
    imageRepository.setNewImage(imageName, userId);
  }

  public void updateImage(String imageName, long userId) {
    imageRepository.updateImage(imageName, userId);
  }

  public String getImageNameByUserId(long userId) {
    return imageRepository.getImageNameByUserId(userId);
  }

  public URI getImagePath(String imageName) throws URISyntaxException {
    return storageService.getImagePath(imageName);
  }

  public void upload(InputStream stream, String fileName) {
    storageService.uploadFile(stream, fileName);
  }
  public void deleteImage(long userId) {
    String imageName = imageRepository.getImageNameByUserId(userId);
    storageService.deleteImage(imageName);
    imageRepository.deleteImage(userId);
  }
}

