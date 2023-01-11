package com.example.users.controller;


import com.example.users.model.User;
import com.example.users.service.ImageService;
import com.example.users.service.ProfileService;
import com.example.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Validated
@Controller
@RequiredArgsConstructor
public class SettingController {

  private final ProfileService profileService;
  private final UserServiceImpl userService;
  private final ImageService imageService;

  @PostMapping("/setting-information")
  protected String showSettingProfile(Model model, @Valid @RequestParam("userId") long userId) {
    User user = userService.findUserByUserId(userId);
    model.addAttribute("user", user);
    return "setting";
  }

  @PostMapping("/setting-correct-name")
  protected String updateNameInProfile(Model model, @RequestParam("userId") long userId,
                                       @RequestParam(required = false, name = "userName") String userName) {
    User user = userService.findUserByUserId(userId);
    profileService.updateUserName(userName, userId);
    model.addAttribute("user", user);
    return "setting";
  }

  @PostMapping("/setting-correct-image")
  protected String updateImageInProfile(Model model,@Valid @RequestParam("userId") long userId,
                                        @RequestParam(required = false, name = "file") MultipartFile file
  ) throws IOException {
    User user = userService.findUserByUserId(userId);
    model.addAttribute("user", user);
    profileService.addNewProfileImage(userId, file);
    return "setting";
  }

  @PostMapping("/setting-delete-image")
  protected String deleteImageInProfile(@Valid @RequestParam("userId") long userId) {
    imageService.deleteImage(userId);
    return "setting";
  }

}
