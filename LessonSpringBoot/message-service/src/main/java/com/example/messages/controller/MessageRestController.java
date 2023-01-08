package com.example.messages.controller;


import com.example.messages.model.Message;
import com.example.messages.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
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

  @PostMapping(path = "/message")
  protected void saveMessage(long myUserId, @Valid @ModelAttribute("targetUserId") long targetUserId,
                             @Valid @RequestParam("message") String message) {
    messageService.saveMessage(myUserId, targetUserId, message);
  }

  @PostMapping(path = "/chat-with-friend")
  protected List<Message> getMessagesByFriend(long myUserId,@Valid @RequestParam("targetUserId") long targetUserId) {
   return messageService.getMessagesByFriendId(myUserId, targetUserId);
  }
}
