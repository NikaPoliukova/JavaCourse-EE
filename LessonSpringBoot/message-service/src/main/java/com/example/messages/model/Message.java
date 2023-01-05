package com.example.messages.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message {
  Long sourceUserId;
  Long targetUserId;
  String messageContent;
  LocalDateTime createdAt;
}