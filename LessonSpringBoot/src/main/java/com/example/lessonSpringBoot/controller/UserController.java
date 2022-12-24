package com.example.lessonSpringBoot.controller;


import com.example.lessonSpringBoot.AuthContext;
import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.service.FriendsServiceImpl;
import com.example.lessonSpringBoot.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@Controller
//@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;
  private final AuthContext authContext;
  private final FriendsServiceImpl friendsService;

  @GetMapping("/users")
  public String findAllUsersByPageAndSearch(final ModelMap model, @RequestParam(name = "searchValue", required = false) String searchValue,
                                            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
    Page<User> page = userService.getFilteredUsers(searchValue, pageNumber - 1, pageSize);
    List<User> users = page.getContent();
    model.addAttribute("currentPage", pageNumber);
    model.addAttribute("totalItems", page.getTotalElements());
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("users", users);
    model.addAttribute("myUserId", authContext.getUserId());
    model.addAttribute("pageSize", pageSize);
    String searchValueAttribute = searchValue != null ? searchValue : "";
    model.addAttribute("searchValue", searchValueAttribute);

    List<Long> friendIds = new ArrayList<>();
    friendsService.getFriendsAndFriendRequests(authContext.getUserId()).forEach(f -> friendIds.add(f.getUserId()));
    model.addAttribute("friendIds", friendIds);
    return "users";
  }

  @SneakyThrows
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public RedirectView createFriendRequest(@Valid @RequestParam("friendUserId") long friendUserId) {
    friendsService.createFriendRequest(authContext.getUserId(), friendUserId);
    return new RedirectView("/users");
  }
  @PostMapping("/profile")
  protected String  redirectToProfile (Model model, @Valid @RequestParam("userId") long userId) {
    User user = userService.findUserByUserId(userId);
    model.addAttribute("user", user);
    return "profile";
  }
  @PostMapping("/setting-information")
  protected String  settingProfile (Model model, @Valid @RequestParam("userId") long userId) {
    User user = userService.findUserByUserId(userId);
    model.addAttribute("user", user);
    return "setting";
  }
}

