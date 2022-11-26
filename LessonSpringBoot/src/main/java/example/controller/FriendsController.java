package example.controller;

import example.model.User;
import example.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller
@RequestMapping("/friends")
@RequiredArgsConstructor

public class FriendsController {

  private FriendsService friendsService;

  @SneakyThrows
  @GetMapping
  public String getAllFriends(final ModelMap model, @RequestAttribute("userId") long userId) {
    List<User> friends = friendsService.findAllFriends(userId);
    model.addAttribute("friends", friends);
    return "friends";
  }

  @SneakyThrows
  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public RedirectView createFriendRequest(@RequestAttribute("userId") long sourceUserId,
                                          @RequestParam("targetUserId") long targetUserId) {
    friendsService.cancelFriendship(sourceUserId, targetUserId);
    return new RedirectView("/friends");
  }
}

