package com.example.messages.model;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Message {
  Long sourceUserId;
  Long targetUserId;
  String messageContent;
  LocalDateTime createdAt;
}