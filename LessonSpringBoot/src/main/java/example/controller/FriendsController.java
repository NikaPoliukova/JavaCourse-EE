package example.controller;

import example.AuthContext;
import example.model.User;
import example.service.FriendsServiceImpl;
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
@RequestMapping("/friends")
@RequiredArgsConstructor

public class FriendsController {
  private final AuthContext authContext;
  private final FriendsServiceImpl friendsService;

  @SneakyThrows
  @GetMapping
  public String getAllFriends(final ModelMap model) {
    List<User> friends = friendsService.findAllById(authContext.getUserId());
    model.addAttribute("friends", friends);
    return "friends";
  }

  @SneakyThrows
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public RedirectView createFriendRequest(@Valid @RequestParam("targetUserId") long targetUserId) {
    friendsService.cancelFriendship(authContext.getUserId(), targetUserId);
    return new RedirectView("/friends");
  }
}

