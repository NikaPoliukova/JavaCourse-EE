package com.example.lessonSpringBoot.config;


import com.example.lessonSpringBoot.AuthContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;


@Configuration
public class SessionConfig {

  @Bean
  @SessionScope
  public AuthContext authContext() {
    return new AuthContext();
  }
}
