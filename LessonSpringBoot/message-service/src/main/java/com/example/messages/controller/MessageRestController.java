package com.example.messages.controller;


import com.example.messages.model.Message;
import com.example.messages.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageRestController {

  private final MessageServiceImpl messageService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/message")
  protected void saveMessage(long myUserId, @RequestParam long targetUserId, @RequestParam String message) {
    messageService.saveMessage(myUserId, targetUserId, message);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/chat-with-friend")
  protected List<Message> getMessagesByFriend(long myUserId, @RequestParam long targetUserId) {
    return messageService.getMessagesByFriendId(myUserId, targetUserId);
  }
}
