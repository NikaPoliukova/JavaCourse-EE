package com.example.messages.controller;


import com.example.messages.AuthContext;
import com.example.messages.model.Message;
import com.example.messages.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageRestController {

  private final MessageServiceImpl messageService;
  private final AuthContext authContext;

  @PostMapping(path = "/form-for-send-message")
  protected String  openFormMessage(Model model, @Valid @RequestParam("targetUserId") long targetUserId) {
    model.addAttribute("targetUserId", targetUserId);
    return "form_for_send_message";
  }

  @PostMapping(path = "/message")
  protected String saveMessageByFriend(@Valid @ModelAttribute("targetUserId") long targetUserId,
                                          @Valid @RequestParam("message") String message) {
    messageService.saveMessage(authContext.getUserId(), targetUserId, message);
    return "redirect:friends";
  }

  @PostMapping(path = "/chat-with-friend")
  protected List<Message> getMessagesByFriend(Model model, @Valid @RequestParam("targetUserId") long targetUserId) {
    List<Message> listMessages = messageService.getMessagesByFriendId(authContext.getUserId(), targetUserId);
    model.addAttribute("listMessages", listMessages);
    return listMessages ;
  }
}
