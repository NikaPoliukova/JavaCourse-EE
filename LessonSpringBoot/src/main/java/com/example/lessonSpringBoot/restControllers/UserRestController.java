package com.example.lessonSpringBoot.restControllers;

import com.example.lessonSpringBoot.AuthContext;
import com.example.lessonSpringBoot.converter.UserConverter;
import com.example.lessonSpringBoot.dto.UserDtoRest;
import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.service.FriendsServiceImpl;
import com.example.lessonSpringBoot.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserServiceImpl userService;
  private final AuthContext authContext;
  private final FriendsServiceImpl friendsService;
  private final UserConverter userConverter;


  @GetMapping
  public List<UserDtoRest> getUsers(@RequestParam(name = "searchValue", required = false) String searchValue,
                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize) {
    Page<User> page = userService.getFilteredUsers(searchValue, pageNumber - 1, pageSize);
    List<User> users = page.getContent();
    return userConverter.toDto(users);
  }

  //это нужно? Нужно сделать это для эндпоинта с получением списка пользователей
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public UserDtoRest createFriendRequest(@RequestBody long friendUserId) {
     friendsService.createFriendRequest(authContext.getUserId(), friendUserId);

    return userConverter.toDto(userService.findUserByUserName(authContext.getUserName()));
  }
}
