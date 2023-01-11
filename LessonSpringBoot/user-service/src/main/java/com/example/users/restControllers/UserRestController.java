package com.example.users.restControllers;

import com.example.users.converter.UserConverter;
import com.example.users.dto.TokensDto;
import com.example.users.dto.UserDtoRest;
import com.example.users.model.User;
import com.example.users.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.example.users.security.jwt.JwtUtils.generateRefreshedAccessToken;
import static com.example.users.security.jwt.JwtUtils.getUsernameFromRefreshToken;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserServiceImpl userService;
  private final UserConverter userConverter;

  @Value("${jwt.secretKey}")
  private String secretKey;


  @GetMapping("/users")
  public List<UserDtoRest> getUsers(@RequestParam(name = "searchValue", required = false) String searchValue,
                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {

    Page<User> page = userService.getFilteredUsers(searchValue, pageNumber - 1, pageSize);
    List<User> users = page.getContent();
    return userConverter.toDto(users);
  }

  @GetMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestHeader(AUTHORIZATION) String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        String username = getUsernameFromRefreshToken(refreshToken, secretKey);
        String accessToken = generateRefreshedAccessToken(username, request.getRequestURL().toString(), secretKey);
        TokensDto dto = new TokensDto();
        dto.setAccess_token(accessToken);
        dto.setRefreshToken(refreshToken);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), dto);
      } catch (Exception e) {
        log.info("Error logging in {}", e.getMessage());
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }
}
