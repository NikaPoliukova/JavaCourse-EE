package com.example.lessonSpringBoot.restControllers;

import com.example.lessonSpringBoot.converter.UserConverter;
import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.model.UserDtoRest;
import com.example.lessonSpringBoot.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserServiceImpl userService;
  private final UserConverter userConverter;


  @GetMapping("/users")
  public List<UserDtoRest> getUsers(@RequestParam(name = "searchValue", required = false) String searchValue,
                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {

    Page<User> page = userService.getFilteredUsers(searchValue, pageNumber - 1, pageSize);
    List<User> users = page.getContent();
    return userConverter.toDto(users);
  }
}
