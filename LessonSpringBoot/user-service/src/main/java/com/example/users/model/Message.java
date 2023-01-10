package com.example.users.model;

import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
@Table(name = "messages")

public class Message {
  Long sourceUserId;
  Long targetUserId;
  String messageContent;
  LocalDateTime createdAt;
}