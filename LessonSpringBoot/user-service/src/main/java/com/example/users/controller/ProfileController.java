package com.example.users.controller;

import com.example.lessonSpringBoot.AuthContext;
import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.service.ImageService;
import com.example.lessonSpringBoot.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Validated
@Controller
@RequiredArgsConstructor
public class ProfileController {

  private final UserServiceImpl userService;
  private final AuthContext authContext;
  private final ImageService imageService;

  @PostMapping("/profile")
  protected String getProfile(Model model, @Valid @RequestParam("userId") long userId) throws URISyntaxException {
    User user = userService.findUserByUserId(userId);
    URI imageUrl = imageService.getImagePath(imageService.getImageNameByUserId(userId));
    long authorizationUserId = authContext.getUserId();
    model.addAttribute("user", user);
    model.addAttribute("imageUrl", imageUrl);
    model.addAttribute("authorizationUserId", authorizationUserId);
    return "profile";
  }


}
