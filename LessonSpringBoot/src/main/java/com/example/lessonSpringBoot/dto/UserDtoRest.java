package com.example.lessonSpringBoot.dto;

import lombok.Value;

import java.util.Date;

@Value
public class UserDtoRest {

  Long userId;
  String userName;
  Date createdDate;
}