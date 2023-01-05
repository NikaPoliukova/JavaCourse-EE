package com.example.users.dto;

import lombok.Data;

@Data
public class TokensDto {
  String access_token;
  String refreshToken;
}
