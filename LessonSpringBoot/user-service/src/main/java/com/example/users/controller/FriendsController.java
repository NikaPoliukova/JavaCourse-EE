package com.example.users.controller;

import com.example.users.AuthContext;
import com.example.users.model.User;
import com.example.users.service.FriendsServiceImpl;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Validated
@Controller
@RequestMapping("/friends")
@RequiredArgsConstructor

public class FriendsController {
  private final AuthContext authContext;
  private final FriendsServiceImpl friendsService;

  @GetMapping
  public String getAllFriends(final ModelMap model) {
    List<User> friends = friendsService.getFriendsByUserIdAndStatus(authContext.getUserId());
    model.addAttribute("friends", friends);
    model.addAttribute("myUserId", authContext.getUserId());
    return "friends";
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public RedirectView cancelFriendRequest(@Valid @RequestParam("targetUserId") long targetUserId) {
    friendsService.cancelFriendship(authContext.getUserId(), targetUserId);
    return new RedirectView("/friends");
  }
}

