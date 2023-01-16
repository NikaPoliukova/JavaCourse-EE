package com.example.users.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*");//localhost//:3000
  }
  /*
  private final AuthContext authContext;


  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/users/page").setViewName("users/page");
    registry.addViewController("/friends").setViewName("friends");
    registry.addViewController("/incoming-friend-requests").setViewName("incoming-friend-requests");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/outgoing-friend-requests").setViewName("outgoing-friend-requests");
    registry.addViewController("/registration").setViewName("registration");
    registry.addViewController("/form-for-send-message").setViewName("form-for-send-message");
    registry.addViewController("/chat-with-friend").setViewName("chat-with-friend");
    registry.addViewController("/profile").setViewName("profile");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MappedInterceptor(new String[]{"/users", "/friends", "/incoming-friend-requests",
        "/outgoing-friend-requests", "/messages", "/form-for-send-message", "/profile",
        "/setting-information", "/setting-correct-name", "/setting-correct-image", "/setting-delete-image"},
        new AuthInterceptor(authContext)));
  }*/
}
