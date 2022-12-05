package com.example.lessonSpringBoot.controller;


import com.example.lessonSpringBoot.AuthContext;
import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.service.FriendsServiceImpl;
import com.example.lessonSpringBoot.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;
  private final AuthContext authContext;
  private final FriendsServiceImpl friendsService;

  @SneakyThrows
  @GetMapping
  public String getUsers(final ModelMap model,
                         @RequestParam(value = "searchValue", required = false) String searchValue) {
    List<User> users;
    List<Long> friendIds = new ArrayList<>();
    List<User> friends = friendsService.getFriendsByUserIdAndStatus(authContext.getUserId());
    for (User friend : friends) {
      friendIds.add(friend.getUserId());
    }
    if (searchValue == null) {
      users = userService.findAllUsers();
    } else {
      users = userService.findByUserNameStartingWith(searchValue);
    }
    model.addAttribute("friendIds", friendIds);
    model.addAttribute("users", users);
    model.addAttribute("myUserId", authContext.getUserId());
    return "users";
  }

  @SneakyThrows
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public RedirectView createFriendRequest(@Valid @RequestParam("friendUserId") long friendUserId) {
    friendsService.createFriendRequest(authContext.getUserId(), friendUserId);
    return new RedirectView("/users");
  }
}

