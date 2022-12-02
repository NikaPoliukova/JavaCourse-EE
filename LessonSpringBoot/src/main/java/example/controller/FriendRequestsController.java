package example.controller;

import example.AuthContext;
import example.model.User;
import example.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class FriendRequestsController {
  private final FriendsService friendsService;
  private final AuthContext authContext;

  @GetMapping("/incoming_friend_requests")
  protected String getIncomingFriendRequests(Model model) {
    List<User> incomingFriendRequests = friendsService.findIncomingFriendRequests(authContext.getUserId());
    model.addAttribute("incomingFriendRequests", incomingFriendRequests);
    return "incoming_friend_requests";
  }

  @PostMapping("/incoming_and_cancel_friend_requests")
  protected RedirectView cancelIncomingFriendRequests( @Valid @RequestParam("cancelUserId") long cancelUserId) {
    friendsService.cancelFriendship(cancelUserId, authContext.getUserId());
    return new RedirectView("/incoming_friend_requests");

  }

  @PostMapping("/incoming_and_added_friend_requests")
  protected RedirectView addIncomingFriendRequests( @Valid @RequestParam("addUserId") long addUserId) {
    friendsService.approveFriendship(addUserId, authContext.getUserId());
    return new RedirectView("/incoming_friend_requests");
  }

  @GetMapping("/outgoing_friend_requests")
  protected String getOutgoingFriendRequest(Model model) {
    List<User> outgoingFriendRequest = friendsService.findOutgoingFriendsRequests(authContext.getUserId());
    model.addAttribute("outgoingFriendRequest", outgoingFriendRequest);
    return "outgoing_friend_requests";
  }

  @PostMapping("/outgoing_friend_requests")
  protected RedirectView postOutgoingFriendRequest(@Valid @RequestParam("cancelUserId") long cancelUserId) {
    friendsService.cancelFriendship(authContext.getUserId(), cancelUserId);
    return new RedirectView("/outgoing_friend_requests");
  }
}


