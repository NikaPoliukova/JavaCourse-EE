package com.example.messages.service;


import com.example.messages.repository.MessageRepository;
import com.example.messages.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl {

  private final MessageRepository messageRepository;

  public void saveMessage(long sourceUserId, long targetUserId, String message_content) {
    messageRepository.saveMessage(sourceUserId, targetUserId, message_content);
  }
  public List<Message> getMessagesByFriendId(long sourceUserId, long targetUserId){
    return messageRepository.getMessagesByFriendId(sourceUserId, targetUserId);
  }
}
