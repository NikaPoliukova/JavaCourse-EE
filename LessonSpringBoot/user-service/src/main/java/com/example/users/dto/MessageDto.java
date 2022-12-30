package com.example.messages.dto;

import lombok.Value;

@Value
public class MessageDto {
  Long sourceUserId;
  Long targetUserId;
  String messageText;
}
