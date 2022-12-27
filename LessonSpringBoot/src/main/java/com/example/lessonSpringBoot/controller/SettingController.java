package com.example.lessonSpringBoot.controller;

import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.service.UserServiceImpl;
import com.example.lessonSpringBoot.service.ImageService;
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

  private final UserServiceImpl userService;
  private final ProfileService profileService;
  private final ImageService imageService;

  @PostMapping("/setting-information")
  protected String showSettingProfile(Model model, @Valid @RequestParam("userId") long userId) {
    User user = userService.findUserByUserId(userId);
    model.addAttribute("user", user);
    return "setting";
  }

  @PostMapping("/setting-correct")
  protected String correctProfile(@Valid @RequestParam("userId") long userId,
                                  @RequestParam(required = false) String userName,
                                  @RequestParam(required = false) int age, @RequestParam(required = false) String gender,
                                  @RequestParam(required = false) String country,
                                  @RequestParam(required = false, name = "file") MultipartFile file
  ) throws IOException {
    profileService.uploadImage(userId, file);
    return "redirect:/profile/" + userId;
  }
}
