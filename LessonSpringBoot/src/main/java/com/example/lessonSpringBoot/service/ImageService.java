package com.example.lessonSpringBoot.service;

import com.example.lessonSpringBoot.repository.ImageRepository;
import com.example.lessonSpringBoot.service.amazonService.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
  private final StorageService storageService;
  private final ImageRepository imageRepository;

  public void setNewImage(String imageName, long userId) {
    imageRepository.setNewImage(imageName,userId);
  }

public void updateImage(String imageName, long userId){
    imageRepository.updateImage(imageName,userId);
}
public void getImageByUserId(long userId){
    imageRepository.getImageByUserId(userId);
}
}

