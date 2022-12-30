package com.example.lessonSpringBoot.service;

import com.example.lessonSpringBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ImageService imageService;
  private final UserRepository userRepository;

  public void addNewProfileImage(long userId, MultipartFile file) throws IOException {
    String imageName = imageService.getImageNameByUserId(userId);
    imageService.upload(file.getInputStream(), file.getOriginalFilename());
    if (!imageName.isEmpty()) {
      imageService.updateImage(file.getOriginalFilename(), userId);
    } else {
      imageService.setNewImage(file.getOriginalFilename(), userId);
    }
  }

 public void updateUserName (String userName, long userId){
    userRepository.updateUserName(userName,userId);
 }
}
