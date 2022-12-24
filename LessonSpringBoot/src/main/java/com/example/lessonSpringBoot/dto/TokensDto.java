package com.example.lessonSpringBoot.dto;

import lombok.Data;

@Data
public class TokensDto {
  String access_token;
  String refreshToken;
}
