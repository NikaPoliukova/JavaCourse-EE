package com.example.lessonSpringBoot.controller;

import com.example.lessonSpringBoot.AuthContext;
import com.example.lessonSpringBoot.model.Message;
import com.example.lessonSpringBoot.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

  private final MessageServiceImpl messageService;
  private final AuthContext authContext;

  @PostMapping("/form-for-send-message")
  protected String  openFormMessage(Model model, @Valid @RequestParam("targetUserId") long targetUserId) {
    model.addAttribute("targetUserId", targetUserId);
    return "form_for_send_message";
  }

  @PostMapping("/message")
  public RedirectView saveMessageByFriend(@Valid @ModelAttribute("targetUserId") long targetUserId,
                                          @Valid @RequestParam("message") String message) {
    messageService.saveMessage(authContext.getUserId(), targetUserId, message);
    return new RedirectView("/friends");
  }

  @PostMapping("/chat-with-friend")
  protected String getMessagesByFriend(Model model, @Valid @RequestParam("targetUserId") long targetUserId) {
    List<Message> listMessages = messageService.getMessagesByFriendId(authContext.getUserId(), targetUserId);
    model.addAttribute("listMessages", listMessages);
    return "chat_with_friend";
  }
}
