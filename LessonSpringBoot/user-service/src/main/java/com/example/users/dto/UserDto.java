package com.example.users.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
  @NotEmpty
  private String userName;
  @NotEmpty
  private String password;
  private Long userId;
}

