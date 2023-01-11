package com.example.users.client;


import com.example.users.model.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "domain", url = "${services.message.url}")
public interface MessageClient {

  @RequestMapping(method = RequestMethod.POST, value = "/message")
  String saveMessage(long myUserId, @RequestParam("targetUserId") long targetUserId,
                     @RequestParam("message") String message);

  @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE, value = "/chat-with-friend")
  List<Message> getMessagesByFriend(@RequestParam("myUserId")long myUserId, @RequestParam("targetUserId") long targetUserId);
}
