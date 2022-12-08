package com.example.lessonSpringBoot.config;


import com.example.lessonSpringBoot.AuthContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;



@Configuration
public class SessionConfig {

  @Bean
  @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AuthContext authContext() {
    return new AuthContext();

  }
}
