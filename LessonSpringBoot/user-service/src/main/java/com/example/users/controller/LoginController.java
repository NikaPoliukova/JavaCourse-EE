package com.example.users.controller;


import com.example.users.AuthContext;
import com.example.users.dto.UserDto;
import com.example.users.model.User;
import com.example.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Validated
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final UserServiceImpl userService;
  private final AuthContext authContext;

  @GetMapping
  protected String userLogin(Model model) {
    model.addAttribute("dto", new UserDto());
    return "login";
  }

  @PostMapping
  protected RedirectView userAuthorization(Model model, @Valid @ModelAttribute("dto") UserDto dto) {
    User user = userService.findUserByUserNameAndPassword(dto.getUserName(), dto.getPassword());
    Long userId = user.getUserId();
    model.addAttribute("userId", userId);
    model.addAttribute("userName", dto.getUserName());
    authContext.setUserId(user.getUserId());
    authContext.setUserName(user.getUserName());
    return new RedirectView("/users");
  }
}
