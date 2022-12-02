package example.controller;


import example.AuthContext;
import example.model.User;
import example.service.FriendsServiceImpl;
import example.service.UserServiceImpl;
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
  public String getUsers(final ModelMap model/*,@RequestParam("search") String searchValue*/) {
    final List<User> users;
   /* if (searchValue == null) {
      users = userService.findUsers();
    } else {
      users = userService.findUserWithSearch(searchValue);
    }*/
    users = userService.findUsers();
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
