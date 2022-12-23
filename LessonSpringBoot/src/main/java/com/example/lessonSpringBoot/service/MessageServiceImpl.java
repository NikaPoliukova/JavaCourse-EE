package com.example.lessonSpringBoot.service;

import com.example.lessonSpringBoot.model.Message;
import com.example.lessonSpringBoot.repository.MessageRepository;
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
  public List<Message> getMessagesByFriendId(long sourceUserId,  long targetUserId){
    return messageRepository.getMessagesByFriendId(sourceUserId, targetUserId);
  }
}
