package com.example.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.users.client")
@SpringBootApplication
public class UsersApplication {
  public static void main(final String[] args) {
    SpringApplication.run(UsersApplication.class, args);
  }
}
