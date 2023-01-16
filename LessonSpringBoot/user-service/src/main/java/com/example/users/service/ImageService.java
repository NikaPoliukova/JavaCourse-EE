package com.example.users.service;

import com.example.users.repository.ImageRepository;
import com.example.users.service.amazonService.AwsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

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

  public Optional<String> getImageNameByUserId(long userId) {
    return imageRepository.getImageNameByUserId(userId);
  }

  public URI getImagePath(String imageName) throws URISyntaxException {
    return storageService.getImagePath(imageName);
  }

  public void upload(InputStream stream, String fileName) {
    storageService.uploadFile(stream, fileName);
  }

  public void deleteImage(long userId) {
    Optional<String> imageName = imageRepository.getImageNameByUserId(userId);
    storageService.deleteImage(imageName.get());
    imageRepository.deleteImage(userId);
  }
}

