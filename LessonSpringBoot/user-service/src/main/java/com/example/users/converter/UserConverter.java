package com.example.users.converter;


import com.example.users.dto.UserDtoRest;
import com.example.users.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserConverter {
  List<UserDtoRest> toDto(List<User> users);
}
