package com.example.users.client;


import com.example.users.model.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "domain", url = "${services.message.url}")
public interface MessageClient {

  @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, value = "/message")
  String saveMessage(@RequestBody long myUserId,
                     @RequestParam("targetUserId") long targetUserId,
                     @RequestParam("message") String message);

  @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, value = "/chat-with-friend")
  List<Message> getMessagesByFriend(long myUserId, @RequestParam("targetUserId") long targetUserId);
}
