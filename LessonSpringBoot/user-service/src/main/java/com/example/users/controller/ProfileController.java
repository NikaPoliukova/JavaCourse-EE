package com.example.users.controller;

import com.example.users.AuthContext;
import com.example.users.model.User;
import com.example.users.service.ImageService;
import com.example.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Validated
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final UserServiceImpl userService;
  private final AuthContext authContext;
  private final ImageService imageService;

  @PostMapping
  protected String showProfile(Model model, @Valid @RequestParam("userId") long userId) throws URISyntaxException {
    User user = userService.findUser(userId);
    String imageName = imageService.getImageNameByUserId(userId);
    URI imageUrl = null;
    if (imageName != null) {
      imageUrl = imageService.getImagePath(imageName);
    }
    long authorizationUserId = authContext.getUserId();
    model.addAttribute("user", user);
    model.addAttribute("imageUrl", imageUrl);
    model.addAttribute("authorizationUserId", authorizationUserId);
    return "profile";
  }

  @GetMapping
  protected String myProfile(Model model) throws URISyntaxException {
    long authorizationUserId = authContext.getUserId();
    User user = userService.findUser(authorizationUserId);
    String imageName = imageService.getImageNameByUserId(authorizationUserId);
    URI imageUrl = null;
    if (imageName != null) {
      imageUrl = imageService.getImagePath(imageName);
    }
    model.addAttribute("user", user);
    model.addAttribute("imageUrl", imageUrl);
    model.addAttribute("authorizationUserId", authorizationUserId);
    return "profile";
  }
}
