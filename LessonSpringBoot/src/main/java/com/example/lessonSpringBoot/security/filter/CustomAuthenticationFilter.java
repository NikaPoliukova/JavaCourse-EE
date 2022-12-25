package com.example.lessonSpringBoot.security.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.lessonSpringBoot.dto.TokensDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.example.lessonSpringBoot.security.jwt.JwtUtils.generateAccessToken;
import static com.example.lessonSpringBoot.security.jwt.JwtUtils.generateRefreshToken;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
public class CustomAuthenticationFilter extends GenericFilterBean {

  private final AuthenticationManager authenticationManager;
  private final String secretKey;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String secretKey) {
    this.authenticationManager = authenticationManager;
    this.secretKey = secretKey;
  }
/*
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("userName");
    String password = request.getParameter("password");
    log.info("username is {}", username);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
    User user = (User) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
    String requestUrl = request.getRequestURL().toString();

    String accessToken = generateAccessToken(user, requestUrl, algorithm);
    String refreshToken = generateRefreshToken(user, requestUrl, algorithm);
    TokensDto dto = new TokensDto();
    dto.setAccess_token(accessToken);
    dto.setRefreshToken(refreshToken);
    response.setContentType(APPLICATION_JSON_VALUE);
     new ObjectMapper().writeValue(response.getOutputStream(), dto);
  }

 */

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String username = request.getParameter("userName");
    String password = request.getParameter("password");
    log.info("username is {}", username);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication =  authenticationManager.authenticate(authenticationToken);
    User user = (User) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
    String requestUrl = request.getRequestURL().toString();

    String accessToken = generateAccessToken(user, requestUrl, algorithm);
    String refreshToken = generateRefreshToken(user, requestUrl, algorithm);
    TokensDto dto = new TokensDto();
    dto.setAccess_token(accessToken);
    dto.setRefreshToken(refreshToken);
    servletResponse.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(servletResponse.getOutputStream(), dto);
  }


}
