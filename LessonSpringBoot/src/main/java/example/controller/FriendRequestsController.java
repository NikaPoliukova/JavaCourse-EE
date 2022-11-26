package example.controller;

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

import java.util.List;

@Controller
@RequiredArgsConstructor

public class FriendRequestsController {
  private FriendsService friendsService;



  @GetMapping("/incoming_friend_requests")
  protected String getIncomingFriendRequests(Model model, @RequestAttribute("userId") long userId) {
    List<User> incomingFriendRequests = friendsService.findIncomingFriendRequests(userId);
    model.addAttribute("incomingFriendRequests", incomingFriendRequests);
    return "incoming_friend_requests";
  }

  @PostMapping("/incoming_friend_requests")
  protected RedirectView addIncomingFriendRequests(@RequestAttribute("userId") long userId,
                                                    @RequestParam("addUserID") long addUserID) {
    friendsService.approveFriendship(addUserID, userId);
    return new RedirectView("/incoming_and_added_friend_requests");
  }
  @PostMapping("/incoming_friend_requests")
  protected RedirectView cancelIncomingFriendRequests(@RequestAttribute("userId") long userId,
                                                    @RequestParam("cancelUserID") long cancelUserID) {
    friendsService.cancelFriendship(cancelUserID, userId);
    return new RedirectView("/incoming_and_added_friend_requests");
  }

  @GetMapping("/outgoing_friend_requests")
  protected String getOutgoingFriendRequest(Model model, @RequestAttribute("userId") long userId) {
    List<User> outgoingFriendRequest = friendsService.findOutgoingFriendsRequests(userId);
    model.addAttribute("outgoingFriendRequest", outgoingFriendRequest);
    return "outgoing_friend_requests";
  }

  @PostMapping("/outgoing_friend_requests")
  protected RedirectView postOutgoingFriendRequest(@RequestAttribute("userId") long userId,
                                                   @RequestParam("cancelUserID") long cancelUserID) {
    friendsService.cancelFriendship(userId, cancelUserID);
    return new RedirectView("/outgoing_friend_requests");
  }
}


