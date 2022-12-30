package com.example.lessonSpringBoot.converter;


import com.example.lessonSpringBoot.dto.UserDtoRest;
import com.example.lessonSpringBoot.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserConverter {
  List<UserDtoRest> toDto(List<User> users);

  UserDtoRest toDto(User user);
}
