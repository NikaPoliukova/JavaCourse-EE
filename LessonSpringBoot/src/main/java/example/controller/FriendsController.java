package example.controller;

import example.AuthContext;
import example.model.User;
import example.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/friends")
@RequiredArgsConstructor

public class FriendsController {
  private final AuthContext authContext;
  private final FriendsService friendsService;

  @SneakyThrows
  @GetMapping
  public String getAllFriends(final ModelMap model) {
    List<User> friends = friendsService.findAllFriends(authContext.getUserId());
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

