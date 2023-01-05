package com.example.users.dto;

import lombok.Value;

import java.util.Date;

@Value
public class UserDtoRest {

  Long userId;
  String userName;
  Date createdDate;
}
