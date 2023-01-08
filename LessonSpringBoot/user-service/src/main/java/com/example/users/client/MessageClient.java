package com.example.users.client;


import com.example.users.model.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "domain", url = "${services.message.url}")
public interface MessageClient {

  @RequestMapping(method = RequestMethod.POST, value = "/message")
  String saveMessage(@RequestBody long myUserId, @Valid @RequestParam("targetUserId") long targetUserId,
                     @Valid @RequestParam("message") String message);

  @RequestMapping(method = RequestMethod.POST, value = "/chat-with-friend")
  List<Message> getMessagesByFriend(long myUserId, @Valid @RequestParam("targetUserId") long targetUserId);
}
